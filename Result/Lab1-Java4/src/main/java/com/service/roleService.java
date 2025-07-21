package com.service;

import com.entity.Role;
import com.util.EntityManagerUtil;
import com.util.ValidationUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.List;

public class roleService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    public Role findByRoleName(String roleName) {
        if (ValidationUtils.isNullOrBlank(roleName)) {
            throw new IllegalArgumentException("Role name cannot be null or empty");
        }

        Role role = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            TypedQuery<Role> query = em.createQuery(
                    "SELECT r FROM Role r WHERE r.roleName = :roleName",
                    Role.class
            );
            query.setParameter("roleName", roleName);

            List<Role> roles = query.getResultList();
            role = roles.isEmpty() ? null : roles.get(0);

            logger.info("Role with name '" + roleName + "'" + (role != null ? " found." : " not found."));
            return role;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding role by name: " + roleName, e);
            return null;
        }
    }
}
