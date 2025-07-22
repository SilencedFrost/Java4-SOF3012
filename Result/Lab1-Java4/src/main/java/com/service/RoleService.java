package com.service;

import com.dto.RoleDTO;
import com.entity.Role;
import com.entity.User;
import com.mapper.RoleMapper;
import com.mapper.UserMapper;
import com.util.EntityManagerUtil;
import com.util.ValidationUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.List;

public class RoleService implements Service<RoleDTO, Integer> {
    private static final Logger logger = Logger.getLogger(RoleService.class.getName());

    @Override
    public List<RoleDTO> findAll() {
        List<Role> roleList = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            roleList = em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
            logger.info("Fetched all users: " + roleList.size() + " users found.");
            return RoleMapper.toDTOList(roleList);
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error fetching users", e);
            return List.of();
        }
    }

    @Override
    public RoleDTO findById(Integer roleId) {
        if (roleId == null) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        Role role = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            role = em.find(Role.class, roleId);
            logger.info("Role with id " + roleId + (role != null ? " found." : " not found."));
            return role != null ? RoleMapper.toDTO(role) : null;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding role by ID", e);
            return null;
        }
    }

    @Override
    public boolean create(RoleDTO roleDTO) {
        if (roleDTO == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            Role role = RoleMapper.toEntity(roleDTO);
            role.setRoleId(null);
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(role);
                tx.commit();
                logger.info("Role created: " + role);
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

    @Override
    public boolean update(RoleDTO roleDTO) {
        if (roleDTO == null || roleDTO.getRoleId() == null) {
            logger.warning("Role or role ID cannot be null or empty");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                Role existingRole = em.find(Role.class, roleDTO.getRoleId());
                if (existingRole != null) {
                    tx.begin();
                    if (roleDTO.getRoleName() != null) existingRole.setRoleName(roleDTO.getRoleName());
                    tx.commit();
                    logger.info("Role with id " + roleDTO.getRoleId() + " updated successfully.");
                    return true;
                } else {
                    logger.warning("Role with id " + roleDTO.getRoleId() + " not found for update.");
                    return false;
                }
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error updating role", e);
                return false;
            }
        }
    }

    @Override
    public boolean delete(Integer roleId) {
        if (roleId == null) {
            logger.warning("ID cannot be null");
            return false;
        }
        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            Role role = em.find(Role.class, roleId);
            if (role == null) {
                logger.warning("Role with id " + roleId + " not found. Deletion skipped.");
                return false;
            }

            try {
                tx.begin();
                em.remove(role);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error deleting user with id " + roleId, e);
                return false;
            }
        }
    }

    public static Role findByRoleName(EntityManager em, String roleName) {
        if (ValidationUtils.isNullOrBlank(roleName)) {
            throw new IllegalArgumentException("Role name cannot be null or empty");
        }

        try {
            TypedQuery<Role> query = em.createQuery(
                    "SELECT r FROM Role r WHERE r.roleName = :roleName",
                    Role.class
            );
            query.setParameter("roleName", roleName);

            List<Role> roles = query.getResultList();
            Role role = roles.isEmpty() ? null : roles.getFirst();

            logger.info("Role with name '" + roleName + "'" + (role != null ? " found." : " not found."));

            return role;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding role by name: " + roleName, e);
            return null;
        }
    }
}
