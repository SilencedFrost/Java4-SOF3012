package com.util;

import com.config.ConfigLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EntityManagerUtil {
    private static final Logger logger = Logger.getLogger(EntityManagerUtil.class.getName());
    private static EntityManagerFactory emf;

    static {
        try {
            ConfigLoader.loadDatabaseConfig();
            emf = Persistence.createEntityManagerFactory("testJava4");
            logger.info("EntityManagerFactory created successfully");

            // Shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (emf != null && emf.isOpen()) {
                    emf.close();
                    logger.info("EntityManagerFactory closed.");
                }
            }));
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Failed to create EntityManagerFactory", e);
            throw new RuntimeException("Failed to initialize EntityManagerFactory", e);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    public static void warmup() {
        logger.info("Starting EntityManager warmup process...");

        EntityManager em = null;
        try {
            // Create and immediately use an EntityManager to initialize connection pool
            em = getEntityManager();
            logger.info("EntityManager created during warmup");

            // Perform a simple database query to establish connection and warm up the pool
            // This uses a standard SQL query that should work on most databases
            Query query = em.createNativeQuery("SELECT 1");
            query.getSingleResult();
            logger.info("Database connection established and warmed up");

            // You can add more warmup operations here if needed, such as:
            // - Loading commonly used entities
            // - Executing frequently used queries
            // - Initializing second-level cache if configured

        } catch (Exception e) {
            logger.log(Level.WARNING, "EntityManager warmup encountered an issue (this may be normal for some databases)", e);
            // Don't throw exception here as warmup failure shouldn't prevent app startup
        } finally {
            if (em != null) {
                try {
                    em.close();
                    logger.info("Warmup EntityManager closed");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error closing warmup EntityManager", e);
                }
            }
        }

        logger.info("EntityManager warmup process completed");
    }
}
