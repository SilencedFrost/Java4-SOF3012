package Managers;

import Models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UserManager {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("PolyOE");
    EntityManager em = factory.createEntityManager();

    public void findall() {
        String jpql="select o from User o";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        List<User> list = query.getResultList();
        list.forEach(user -> {
            String fullname = user.getFullname();
            boolean admin = user.getAdmin();
            System.out.println(fullname + ": " + admin);
        });
    }

    public void findById() {
        User user = em.find(User.class, "SD001");
        String fullname = user.getFullname();
        boolean admin = user.getAdmin();
        System.out.println(fullname + ": " + admin);
    }

    public void create(){
        User user = new User("U01", "123", "teo@gmail.com", "Tèo", false);
        try{
           em.getTransaction().begin();
           em.persist(user);
           em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public void update() {
        User user = em.find(User.class, "U01");
        user.setFullname("Nguyễn Văn Tèo");
        user.setEmail("teonv@gmail.com");
        try{
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public void deleteById() {
        User user = em.find(User.class, "U01");
        try{
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
}
