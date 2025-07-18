package com.service;

import com.entity.User;
import jakarta.persistence.*;
import com.util.EntityManagerUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService implements Service<User>{
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    public UserService() {
    }

    @Override
    public List<User> getAll() {
        List<User> userList = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            userList = em.createQuery("SELECT u FROM User u", User.class).getResultList();
            logger.info("Fetched all users: " + userList.size() + " users found.");
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error fetching users", e);
        }
        return userList != null ? userList : List.of();
    }

    @Override
    public User getById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        User user = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            user = em.find(User.class, id);
            logger.info("User with id " + id + (user != null ? " found." : " not found."));
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding user by ID", e);
        }
        return user;
    }

    // Manual creation method
    public void create(String id, String password, String email, String fullname) {
        create(new User(id, password, email, fullname));
    }

    // Object creation method
    @Override
    public boolean create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(user);
                tx.commit();
                logger.info("User created: " + user);
            } catch (PersistenceException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error creating user", e);
                return false;
            }
        }
        return true;
    }

    /**
     * Updates a user with the given values. Only non-null fields will be applied to the existing user.
     * <p>
     * This method allows partial updates: if a field is {@code null}, it will be ignored and the existing value will be preserved.
     * </p>
     *
     * @param id        the ID of the user to update (must not be null or blank)
     * @param password  the new passwordHash, or {@code null} to leave unchanged
     * @param fullname  the new full name, or {@code null} to leave unchanged
     * @param email     the new email, or {@code null} to leave unchanged
     * @throws IllegalArgumentException if {@code id} is null or blank
     */
    public void update(String id, String password, String fullname, String email) {
        update(new User(id, password, email, fullname));
    }

    // Object update method
    @Override
    public boolean update(User updatedUser) {
        if (updatedUser == null || updatedUser.getId() == null || updatedUser.getId().trim().isEmpty()) {
            logger.warning("User or User ID cannot be null or empty");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                User existingUser = em.find(User.class, updatedUser.getId());
                tx.begin();
                if (existingUser != null) {
                    if (updatedUser.getFullname() != null) {
                        existingUser.setFullname(updatedUser.getFullname());
                    }
                    if (updatedUser.getPasswordHash() != null) {
                        existingUser.setPasswordHash(updatedUser.getPasswordHash());
                    }
                    if (updatedUser.getEmail() != null) {
                        existingUser.setEmail(updatedUser.getEmail());
                    }
                    em.merge(existingUser);
                } else {
                    logger.warning("User with id " + updatedUser.getId() + " not found for update.");
                    return false;
                }
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error updating user", e);
                return false;
            }
        }
    }

    //Manual delete method
    @Override
    public boolean delete(String id) {
        if (id == null) {
            logger.warning("ID cannot be null");
            return false;
        }
        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            User user = em.find(User.class, id);
            if (user == null) {
                logger.warning("User with id " + id + " not found. Deletion skipped.");
                return false;
            }

            try {
                tx.begin();
                em.remove(user);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error deleting user with id " + id, e);
                return false;
            }
        }
    }

    // Object delete method
    public boolean delete(User user) {
        if (user == null) {
            logger.warning("User cannot be null");
            return false;
        }
        return delete(user.getId());
    }
}
