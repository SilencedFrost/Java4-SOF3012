package com.listener;

import com.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class EntityManagerListener implements ServletContextListener{
    Logger logger = Logger.getLogger(EntityManagerListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Starting EntityManager warmup...");
        try {
            EntityManagerUtil.warmup();
            logger.info("EntityManager warmup completed successfully");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to warmup EntityManager", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        logger.info("Shutting down EntityManager...");
        EntityManagerUtil.close();
        logger.info("EntityManager shutdown completed");
    }
}