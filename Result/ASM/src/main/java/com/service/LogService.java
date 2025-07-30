package com.service;

import com.dto.LogDTO;
import com.entity.Log;
import com.entity.User;
import com.entity.Video;
import com.mapper.LogMapper;
import com.util.EntityManagerUtil;
import com.util.ValidationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor
public class LogService implements Service<LogDTO, Long>{
    private static final Logger logger = Logger.getLogger(LogService.class.getName());

    @Override
    public List<LogDTO> findAll() {
        List<Log> logList = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            logList = em.createQuery("SELECT l FROM Log l", Log.class).getResultList();
            logger.info("Fetched all logs: " + logList.size() + " logs found.");
            return LogMapper.toDTOList(logList);
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error fetching logs", e);
            return new ArrayList<>();
        }
    }

    @Override
    public LogDTO findById(Long logId) {
        if (logId == null) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        Log log;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            log = em.find(Log.class, logId);
            logger.info("Log with id " + logId + (log != null ? " found." : " not found."));
            return log != null ? LogMapper.toDTO(log) : null;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding log by ID", e);
            return null;
        }
    }

    // Manual creation method
    public boolean create(String link, String userId) {
        return create(new LogDTO(link, userId));
    }

    // Object creation method
    @Override
    public boolean create(LogDTO logDTO) {
        if (logDTO == null) {
            throw new IllegalArgumentException("Log cannot be null");
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            User user = em.find(User.class, logDTO.getUserId());
            if(user == null) {
                logger.warning("user " + logDTO.getUserId() + " not found");
                return false;
            }
            Log log = LogMapper.toEntity(logDTO, user);
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(log);
                tx.commit();
                logger.info("Log created: " + log);
                return true;
            } catch (PersistenceException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error creating log", e);
                return false;
            }
        }
    }

    public boolean update(Long logId, String link, String userId) {
        return update(new LogDTO(logId, link, userId));
    }

    // Object update method
    @Override
    public boolean update(LogDTO logDTO) {
        if (logDTO == null || logDTO.getLogId() == null) {
            logger.warning("Log or Log ID cannot be null or empty");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                Log existingLog = em.find(Log.class, logDTO.getLogId());
                if (existingLog != null) {
                    tx.begin();
                    if(!ValidationUtil.isNullOrBlank(logDTO.getLink())) existingLog.setLink(logDTO.getLink());

                    User user = em.find(User.class, logDTO.getUserId());
                    if(user != null) user.addLog(existingLog);

                    tx.commit();
                    logger.info("Log with id " + logDTO.getLogId() + " updated successfully.");
                    return true;
                } else {
                    logger.warning("Log with id " + logDTO.getLogId() + " not found for update.");
                    return false;
                }
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error updating log", e);
                return false;
            }
        }
    }

    //Manual delete method
    @Override
    public boolean delete(Long logId) {
        if (logId == null) {
            logger.warning("ID cannot be null");
            return false;
        }
        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            Log log = em.find(Log.class, logId);
            if (log == null) {
                logger.warning("Log with id " + logId + " not found. Deletion skipped.");
                return false;
            }

            try {
                tx.begin();
                em.remove(log);
                new User().removeLog(log);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error deleting log with id " + logId, e);
                return false;
            }
        }
    }

    // Object delete method
    public boolean delete(LogDTO logDTO) {
        if (logDTO == null) {
            logger.warning("Log cannot be null");
            return false;
        }
        return delete(logDTO.getLogId());
    }
}
