package com.service;

import com.entity.Role;
import jakarta.persistence.EntityManager;
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
}
