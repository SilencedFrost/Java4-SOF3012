package com.listener;

import com.entity.Visit;
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
public class EntityManagerListener implements ServletContextListener, HttpSessionListener {
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


        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            Visit visit = em.find(Visit.class, 1);
            if(visit == null){
                logger.info("visit is null, creating new visit entity");
                tx.begin();
                em.persist(new Visit());
                tx.commit();
                logger.info("created visit entity");
                visit = em.find(Visit.class, 1);
            }
            logger.info("visit entity found");
            sce.getServletContext().setAttribute("visits", visit.getVisitCount());
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Something happened", e);
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext sc = se.getSession().getServletContext();
        Integer visitCount = (Integer) sc.getAttribute("visits");
        visitCount++;
        sc.setAttribute("visits", visitCount);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Visit visit = em.find(Visit.class, 1);
            visit.setVisitCount((Integer) sce.getServletContext().getAttribute("visits"));
            tx.commit();
        }   catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Something happened", e);
        }

        logger.info("Shutting down EntityManager...");
        EntityManagerUtil.close();
        logger.info("EntityManager shutdown completed");
    }
}