package service;

import entity.User;
import jakarta.persistence.*;

import java.util.List;

public class UserService {
    private EntityManagerFactory emf;
    private EntityManager em;

    public UserService() {
        emf = Persistence.createEntityManagerFactory("PolyOE");
        em = emf.createEntityManager();
    }

    private void openEntityManager() {
        if (!em.isOpen() || em == null) {
            em = emf.createEntityManager();
        }
    }

    private void closeEntityManager() {
        if (em.isOpen() || em != null) {
            em.close();
        }
    }

    public List<User> getAll() {
        List<User> listUser = null;
        openEntityManager();
        try {
            listUser = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            closeEntityManager();
        }
        return listUser;
    }

    public User findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        User user = null;
        openEntityManager();
        try {
            user = em.find(User.class, id);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        finally {
            closeEntityManager();
        }
        return user;
    }

    public void create(String id, String password, String email, String fullname, boolean isAdmin) {
        User user = new User(id, password, email, fullname, isAdmin);
        openEntityManager();
        EntityTransaction tx = em.getTransaction();
        try{
           tx.begin();
           em.persist(user);
           tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            closeEntityManager();
        }
    }

    // Selective update method
    public void update(String id, String password, String fullname, String email, Boolean isAdmin) {
        openEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User user = em.find(User.class, id);

            if (user != null) {
                if (password != null) {
                    user.setPassword(password);
                }
                if (fullname != null) {
                    user.setFullname(fullname);
                }
                if (email != null) {
                    user.setEmail(email);
                }
                if (isAdmin != null) {
                    user.setAdmin(isAdmin);
                }

                em.merge(user);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            closeEntityManager();
        }
    }

    // Object update method
    public void update(User updatedUser) {
        openEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User existingUser = em.find(User.class, updatedUser.getId());

            if (existingUser != null) {
                existingUser.setFullname(updatedUser.getFullname());
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setPassword(updatedUser.getPassword());
                existingUser.setAdmin(updatedUser.getAdmin());

                em.merge(existingUser);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            closeEntityManager();
        }
    }

    public void deleteById() {
        openEntityManager();
        User user = em.find(User.class, "U01");
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin();
            em.remove(user);
            tx.commit();
        } catch (Exception e) {
            if(tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            closeEntityManager();
        }
    }
}
