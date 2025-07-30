package com.service;

import com.dto.ShareDTO;
import com.entity.Share;
import com.entity.User;
import com.entity.Video;
import com.mapper.ShareMapper;
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
public class ShareService implements Service<ShareDTO, Long>{
    private static final Logger logger = Logger.getLogger(ShareService.class.getName());

    @Override
    public List<ShareDTO> findAll() {
        List<Share> shareList = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            shareList = em.createQuery("SELECT s FROM Share s", Share.class).getResultList();
            logger.info("Fetched all shares: " + shareList.size() + " shares found.");
            return ShareMapper.toDTOList(shareList);
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error fetching shares", e);
            return new ArrayList<>();
        }
    }

    @Override
    public ShareDTO findById(Long shareId) {
        if (shareId == null) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        Share share;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            share = em.find(Share.class, shareId);
            logger.info("Share with id " + shareId + (share != null ? " found." : " not found."));
            return share != null ? ShareMapper.toDTO(share) : null;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding share by ID", e);
            return null;
        }
    }

    // Manual creation method
    public boolean create(String userId, String videoId, String email) {
        return create(new ShareDTO(userId, videoId, email));
    }

    // Object creation method
    @Override
    public boolean create(ShareDTO shareDTO) {
        if (shareDTO == null) {
            throw new IllegalArgumentException("Share cannot be null");
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            User user = em.find(User.class, shareDTO.getUserId());
            Video video = em.find(Video.class, shareDTO.getVideoId());
            if(user == null) {
                logger.warning("user " + shareDTO.getUserId() + " not found");
                return false;
            }
            if(video == null) {
                logger.warning("video " + shareDTO.getVideoId() + " not found");
                return false;
            }
            Share share = ShareMapper.toEntity(shareDTO, user, video);
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(share);
                tx.commit();
                logger.info("Share created: " + share);
                return true;
            } catch (PersistenceException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error creating share", e);
                return false;
            }
        }
    }

    public boolean update(Long shareId, String userId, String videoId, String email) {
        return update(new ShareDTO(shareId, userId, videoId, email));
    }

    // Object update method
    @Override
    public boolean update(ShareDTO shareDTO) {
        if (shareDTO == null || shareDTO.getShareId() == null) {
            logger.warning("Share or Share ID cannot be null or empty");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                Share existingShare = em.find(Share.class, shareDTO.getShareId());
                if (existingShare != null) {
                    tx.begin();
                    if(!ValidationUtil.isNullOrBlank(shareDTO.getReceiveEmail())) existingShare.setReceiveEmail(shareDTO.getReceiveEmail());

                    User user = em.find(User.class, shareDTO.getUserId());
                    if(user != null) user.addShare(existingShare);

                    Video video = em.find(Video.class, shareDTO.getVideoId());
                    if(video != null) video.addShare(existingShare);

                    tx.commit();
                    logger.info("Share with id " + shareDTO.getShareId() + " updated successfully.");
                    return true;
                } else {
                    logger.warning("Share with id " + shareDTO.getShareId() + " not found for update.");
                    return false;
                }
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error updating share", e);
                return false;
            }
        }
    }

    //Manual delete method
    @Override
    public boolean delete(Long shareId) {
        if (shareId == null) {
            logger.warning("ID cannot be null");
            return false;
        }
        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            Share share = em.find(Share.class, shareId);
            if (share == null) {
                logger.warning("Share with id " + shareId + " not found. Deletion skipped.");
                return false;
            }

            try {
                tx.begin();
                em.remove(share);
                new User().removeShare(share);
                new Video().removeShare(share);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error deleting share with id " + shareId, e);
                return false;
            }
        }
    }

    // Object delete method
    public boolean delete(ShareDTO shareDTO) {
        if (shareDTO == null) {
            logger.warning("Share cannot be null");
            return false;
        }
        return delete(shareDTO.getShareId());
    }
}
