/* package com.service;

import com.dto.UserDTO;
import com.entity.User;
import com.mapper.UserMapper;
import com.util.EntityManagerUtil;
import com.util.ValidationUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor
public class VideoService implements Service<UserDTO>{
    private static final Logger logger = Logger.getLogger(VideoService.class.getName());

    @Override
    public List<UserDTO> findAll() {
        List<User> userList = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            userList = em.createQuery("SELECT u FROM User u", User.class).getResultList();
            logger.info("Fetched all users: " + userList.size() + " users found.");
            return UserMapper.toDTOList(userList);
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error fetching users", e);
            return List.of();
        }
    }

    @Override
    public UserDTO findById(String id) {
        if (ValidationUtils.isNullOrBlank(id)) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        User user = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            user = em.find(User.class, id);
            logger.info("User with id " + id + (user != null ? " found." : " not found."));
            return user != null ? UserMapper.toDTO(user) : null;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding user by ID", e);
            return null;
        }
    }

    // Manual creation method
    public boolean create(String id, String password, String fullname, String email) {
        return create(new UserDTO(id, password, fullname, email));
    }

    // Object creation method
    @Override
    public boolean create(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        User user = UserMapper.toEntity(userDTO);

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(user);
                tx.commit();
                logger.info("User created: " + user);
                return true;
            } catch (PersistenceException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error creating user", e);
                return false;
            }
        }
    }

    public boolean update(String id, String password, String fullname, String email) {
        return update(new UserDTO(id, password, fullname, email));
    }

    // Object update method
    @Override
    public boolean update(UserDTO updatedUser) {
        if (updatedUser == null || ValidationUtils.isNullOrBlank(updatedUser.getUserId())) {
            logger.warning("User or User ID cannot be null or empty");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                User existingUser = em.find(User.class, updatedUser.getUserId());
                if (existingUser != null) {
                    tx.begin();
                    if (updatedUser.getFullName() != null) {
                        existingUser.setFullName(updatedUser.getFullName());
                    }
                    if (updatedUser.getPasswordHash() != null) {
                        existingUser.setPasswordHash(updatedUser.getPasswordHash());
                    }
                    if (updatedUser.getEmail() != null) {
                        existingUser.setEmail(updatedUser.getEmail());
                    }
                    tx.commit();
                    logger.info("User with id " + updatedUser.getUserId() + " updated successfully.");
                    return true;
                } else {
                    logger.warning("User with id " + updatedUser.getUserId() + " not found for update.");
                    return false;
                }
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
    public boolean delete(UserDTO user) {
        if (user == null) {
            logger.warning("User cannot be null");
            return false;
        }
        return delete(user.getUserId());
    }
} */
