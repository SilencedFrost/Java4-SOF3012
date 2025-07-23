package com.service;

import com.dto.UserDTO;
import com.entity.Role;
import com.entity.User;
import com.mapper.UserMapper;
import com.util.ValidationUtils;
import jakarta.persistence.*;
import com.util.EntityManagerUtil;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor
public class UserService implements Service<UserDTO, String>{
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

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
    public UserDTO findById(String userId) {
        if (ValidationUtils.isNullOrBlank(userId)) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        User user = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            user = em.find(User.class, userId);
            logger.info("User with id " + userId + (user != null ? " found." : " not found."));
            return user != null ? UserMapper.toDTO(user) : null;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding user by ID", e);
            return null;
        }
    }

    public List<UserDTO> findByIdLike(String partialId){
        if (ValidationUtils.isNullOrBlank(partialId)) {
            throw new IllegalArgumentException("Partial ID cannot be null or empty");
        }

        List<User> userList = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            userList = em.createQuery(
                            "SELECT u FROM User u WHERE u.userId LIKE :partialId",
                            User.class
                    )
                    .setParameter("partialId", "%" + partialId + "%")
                    .getResultList();

            logger.info("Found " + userList.size() + " users with ID containing: " + partialId);
            return UserMapper.toDTOList(userList);
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding users by partial ID: " + partialId, e);
            return List.of();
        }
    }

    // Manual creation method
    public boolean create(String userId, String password, String fullName, String email, String roleName) {
        return create(new UserDTO(userId, password, fullName, email, roleName));
    }

    // Object creation method
    @Override
    public boolean create(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            User user = UserMapper.toEntity(userDTO, RoleService.findByRoleName(em, userDTO.getRoleName()));
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

    public boolean update(String userId, String password, String fullName, String email, String roleName) {
        return update(new UserDTO(userId, password, fullName, email, roleName));
    }

    // Object update method
    @Override
    public boolean update(UserDTO userDTO) {
        if (userDTO == null || ValidationUtils.isNullOrBlank(userDTO.getUserId())) {
            logger.warning("User or User ID cannot be null or empty");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                User existingUser = em.find(User.class, userDTO.getUserId());
                if (existingUser != null) {
                    tx.begin();
                    if (userDTO.getFullName() != null) existingUser.setFullName(userDTO.getFullName());
                    if (userDTO.getPasswordHash() != null) existingUser.setPasswordHash(userDTO.getPasswordHash());
                    if (userDTO.getEmail() != null) existingUser.setEmail(userDTO.getEmail());
                    Role role = RoleService.findByRoleName(em, userDTO.getRoleName());
                    if (role == null) role = em.find(Role.class, 1);
                    role.addUser(existingUser);
                    tx.commit();
                    logger.info("User with id " + userDTO.getUserId() + " updated successfully.");
                    return true;
                } else {
                    logger.warning("User with id " + userDTO.getUserId() + " not found for update.");
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
    public boolean delete(String userId) {
        if (userId == null) {
            logger.warning("ID cannot be null");
            return false;
        }
        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            User user = em.find(User.class, userId);
            if (user == null) {
                logger.warning("User with id " + userId + " not found. Deletion skipped.");
                return false;
            }

            try {
                tx.begin();
                em.remove(user);
                new Role().removeUser(user);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error deleting user with id " + userId, e);
                return false;
            }
        }
    }

    // Object delete method
    public boolean delete(UserDTO userDTO) {
        if (userDTO == null) {
            logger.warning("User cannot be null");
            return false;
        }
        return delete(userDTO.getUserId());
    }
}
