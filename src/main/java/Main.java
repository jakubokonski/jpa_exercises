import entity.Todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class Main {
    private static final String PERSISTENCE_UNIT_NAME = "todos";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();


        Query q = em.createQuery("select t from Todo t");
        List<Todo> todoList = q.getResultList();
        for (Todo todo : todoList) {
            System.out.println(todo);
        }
        System.out.println("Size: " + todoList.size());

        em.getTransaction().begin();
        Todo todo1 = new Todo();
        todo1.setDescription(LocalDateTime.now().toString());
        todo1.setSummary("Summary");
        em.persist(todo1);
        em.getTransaction().commit();

        q = em.createQuery("select t from Todo t");
        todoList = q.getResultList();
        for (Todo todo : todoList) {
            System.out.println(todo);
        }
        System.out.println("Size: " + todoList.size());

        em.getTransaction().begin();
        Todo todo2 = em.find(Todo.class, 1L);
        todo2.setSummary("Modified summary");
        System.out.println(todo2.toString());
        em.getTransaction().commit();

        em.getTransaction().begin();
        Todo todo3 = em.find(Todo.class, 14L);
        em.remove(todo3);
        em.getTransaction().commit();

        System.out.println("Last list:");
        q = em.createQuery("select t from Todo t");
        todoList = q.getResultList();
        for (Todo todo : todoList) {
            System.out.println(todo);
        }
        System.out.println("Size: " + todoList.size());

        em.close();
        factory.close();
    }
}