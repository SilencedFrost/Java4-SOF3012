package com.service;

import com.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import com.util.EntityManagerUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoleService implements Service<Role> {
    private static final Logger logger = Logger.getLogger(RoleService.class.getName());

    @Override
    public List<Role> getAll() {
        List<Role> roleList = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            roleList = em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
            logger.info("Fetched all roles: " + roleList.size() + " roles found.");
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error fetching roles", e);
        }
        return roleList;
    }

    @Override
    public Role getById(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        Role role = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            role = em.createQuery("SELECT r FROM Role r WHERE r.userId = :userId", Role.class).setParameter("userId", userId).getSingleResult();
            logger.info("Role with userId " + userId + (role != null ? " found." : " not found."));
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding role by User ID", e);
        }
        return role;
    }

    // Manual creation method
    public void create(String userId, String name) {
        create(new Role(userId, name));
    }

    @Override
    public boolean create(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(role);
                tx.commit();
                logger.info("Role created successfully: " + role);
                return true;
            } catch (PersistenceException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error creating role", e);
                return false;
            }
        }
    }

    public boolean update(String userId, String roleName) {
        return update(new Role(userId, roleName));
    }

    @Override
    public boolean update(Role role) {
        if (role == null || role.getUserId() == null || role.getUserId().trim().isEmpty()) {
            logger.warning("Role or UserID cannot be null");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                Role existingRole = getById(role.getUserId());
                tx.begin();
                if (existingRole != null) {
                    existingRole.setRole(role.getRole());
                    em.merge(existingRole);
                    tx.commit();
                    logger.info("Role updated successfully for userId: " + role.getUserId());
                    return true;
                } else {
                    logger.warning("UserId " + role.getUserId() + " does not exist to update role.");
                    return false;
                }
            } catch (PersistenceException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error updating role", e);
                return false;
            }
        }
    }

    @Override
    public boolean delete(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            logger.warning("User ID cannot be null or empty");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                Role role = getById(userId);
                if (role == null) {
                    logger.warning("Role with userId " + userId + " not found. Deletion skipped.");
                    return false;
                }
                tx.begin();
                em.remove(role);
                tx.commit();
                logger.info("Role deleted successfully for userId: " + userId);
                return true;
            } catch (PersistenceException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error deleting role", e);
                return false;
            }
        }
    }
}
