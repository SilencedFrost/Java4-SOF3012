package com.service;

import com.dto.VideoDTO;
import com.entity.Video;
import com.mapper.VideoMapper;
import com.util.EntityManagerUtil;
import com.util.ValidationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor
public class VideoService implements Service<VideoDTO, String>{
    private static final Logger logger = Logger.getLogger(VideoService.class.getName());

    @Override
    public List<VideoDTO> findAll() {
        List<Video> videoList = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            videoList = em.createQuery("SELECT v FROM Video v", Video.class).getResultList();
            logger.info("Fetched all videos: " + videoList.size() + " videos found.");
            return VideoMapper.toDTOList(videoList);
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error fetching videos", e);
            return List.of();
        }
    }

    @Override
    public VideoDTO findById(String videoId) {
        if (ValidationUtil.isNullOrBlank(videoId)) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        Video video = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            video = em.find(Video.class, videoId);
            logger.info("Video with id " + videoId + (video != null ? " found." : " not found."));
            return video != null ? VideoMapper.toDTO(video) : null;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding video by ID", e);
            return null;
        }
    }

    public List<VideoDTO> findByIdLike(String partialId){
        if (ValidationUtil.isNullOrBlank(partialId)) {
            throw new IllegalArgumentException("Partial ID cannot be null or empty");
        }

        List<Video> videoList = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            videoList = em.createQuery(
                            "SELECT u FROM Video u WHERE u.videoId LIKE :partialId",
                            Video.class
                    )
                    .setParameter("partialId", "%" + partialId + "%")
                    .getResultList();

            logger.info("Found " + videoList.size() + " videos with ID containing: " + partialId);
            return VideoMapper.toDTOList(videoList);
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding videos by partial ID: " + partialId, e);
            return List.of();
        }
    }

    // Manual creation method
    public boolean create(String videoId, String title, String poster, String link, long views, String description, Boolean active) {
        return create(new VideoDTO(videoId, title, poster, link, views, description, active));
    }

    // Object creation method
    @Override
    public boolean create(VideoDTO videoDTO) {
        if (videoDTO == null) {
            throw new IllegalArgumentException("Video cannot be null");
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            Video video = VideoMapper.toEntity(videoDTO);
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(video);
                tx.commit();
                logger.info("Video created: " + video);
                return true;
            } catch (PersistenceException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error creating video", e);
                return false;
            }
        }
    }

    public boolean update(String videoId, String title, String poster, String link, long views, String description, Boolean active) {
        return update(new VideoDTO(videoId, title, poster, link, views, description, active));
    }

    // Object update method
    @Override
    public boolean update(VideoDTO videoDTO) {
        if (videoDTO == null || ValidationUtil.isNullOrBlank(videoDTO.getVideoId())) {
            logger.warning("Video or Video ID cannot be null or empty");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                Video existingVideo = em.find(Video.class, videoDTO.getVideoId());
                if (existingVideo != null) {
                    tx.begin();
                    if (videoDTO.getTitle()!= null) existingVideo.setTitle(videoDTO.getTitle());
                    if (videoDTO.getPoster() != null) existingVideo.setPoster(videoDTO.getPoster());
                    if (videoDTO.getViews() != null) existingVideo.setViews(videoDTO.getViews());
                    if (videoDTO.getActive() != null) existingVideo.setActive(videoDTO.getActive());
                    tx.commit();
                    logger.info("Video with id " + videoDTO.getVideoId() + " updated successfully.");
                    return true;
                } else {
                    logger.warning("Video with id " + videoDTO.getVideoId() + " not found for update.");
                    return false;
                }
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error updating video", e);
                return false;
            }
        }
    }

    //Manual delete method
    @Override
    public boolean delete(String videoId) {
        if (videoId == null) {
            logger.warning("ID cannot be null");
            return false;
        }
        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            Video video = em.find(Video.class, videoId);
            if (video == null) {
                logger.warning("Video with id " + videoId + " not found. Deletion skipped.");
                return false;
            }

            try {
                tx.begin();
                em.remove(video);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error deleting video with id " + videoId, e);
                return false;
            }
        }
    }

    // Object delete method
    public boolean delete(VideoDTO videoDTO) {
        if (videoDTO == null) {
            logger.warning("Video cannot be null");
            return false;
        }
        return delete(videoDTO.getVideoId());
    }
}
