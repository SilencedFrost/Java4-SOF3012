package com.service;

import com.dto.FavouriteDTO;
import com.entity.Favourite;
import com.entity.User;
import com.entity.Video;
import com.mapper.FavouriteMapper;
import com.util.EntityManagerUtil;
import com.util.ValidationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor
public class FavouriteService implements Service<FavouriteDTO, Long>{
    private static final Logger logger = Logger.getLogger(FavouriteService.class.getName());

    @Override
    public List<FavouriteDTO> findAll() {
        List<Favourite> favouriteList = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            favouriteList = em.createQuery("SELECT f FROM Favourite f", Favourite.class).getResultList();
            logger.info("Fetched all favourites: " + favouriteList.size() + " favourites found.");
            return FavouriteMapper.toDTOList(favouriteList);
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error fetching favourites", e);
            return List.of();
        }
    }

    @Override
    public FavouriteDTO findById(Long favouriteId) {
        if (favouriteId == null) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        Favourite favourite = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            favourite = em.find(Favourite.class, favouriteId);
            logger.info("Favourite with id " + favouriteId + (favourite != null ? " found." : " not found."));
            return favourite != null ? FavouriteMapper.toDTO(favourite) : null;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding favourite by ID", e);
            return null;
        }
    }

    // Manual creation method
    public boolean create(String userId, String videoId) {
        return create(new FavouriteDTO(userId, videoId));
    }

    // Object creation method
    @Override
    public boolean create(FavouriteDTO favouriteDTO) {
        if (favouriteDTO == null) {
            throw new IllegalArgumentException("Favourite cannot be null");
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            User user = em.find(User.class, favouriteDTO.getUserId());
            Video video = em.find(Video.class, favouriteDTO.getVideoId());

            if(user == null){
                logger.warning("user not found");
                return false;
            }
            if(video == null) {
                logger.warning("video not found");
                return false;
            }

            Favourite favourite = FavouriteMapper.toEntity(favouriteDTO, user, video);
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(favourite);
                tx.commit();
                logger.info("Favourite created: " + favourite);
                return true;
            } catch (PersistenceException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error creating favourite", e);
                return false;
            }
        }
    }

    public boolean update(Long favouriteId, String userId, String videoId, LocalDate favouriteDate) {
        return update(new FavouriteDTO(favouriteId, userId, videoId, favouriteDate));
    }

    // Object update method
    @Override
    public boolean update(FavouriteDTO favouriteDTO) {
        if (favouriteDTO == null || favouriteDTO.getFavouriteId() == null) {
            logger.warning("Favourite or Favourite ID cannot be null or empty");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                Favourite existingFavourite = em.find(Favourite.class, favouriteDTO.getFavouriteId());
                if (existingFavourite != null) {
                    tx.begin();
                    if (favouriteDTO.getFavouriteDate() != null) existingFavourite.setFavouriteDate(favouriteDTO.getFavouriteDate());

                    User user = em.find(User.class, favouriteDTO.getUserId());
                    if(user != null) user.addFavourite(existingFavourite);

                    Video video = em.find(Video.class, favouriteDTO.getVideoId());
                    if(video != null) video.addFavourite(existingFavourite);

                    tx.commit();
                    logger.info("Favourite with id " + favouriteDTO.getFavouriteId() + " updated successfully.");
                    return true;
                } else {
                    logger.warning("Favourite with id " + favouriteDTO.getFavouriteId() + " not found for update.");
                    return false;
                }
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error updating favourite", e);
                return false;
            }
        }
    }

    //Manual delete method
    @Override
    public boolean delete(Long favouriteId) {
        if (favouriteId == null) {
            logger.warning("ID cannot be null");
            return false;
        }
        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            Favourite favourite = em.find(Favourite.class, favouriteId);
            if (favourite == null) {
                logger.warning("Favourite with id " + favouriteId + " not found. Deletion skipped.");
                return false;
            }

            try {
                tx.begin();
                em.remove(favourite);
                new User().removeFavourite(favourite);
                new Video().removeFavourite(favourite);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error deleting favourite with id " + favouriteId, e);
                return false;
            }
        }
    }

    // Object delete method
    public boolean delete(FavouriteDTO favouriteDTO) {
        if (favouriteDTO == null) {
            logger.warning("Favourite cannot be null");
            return false;
        }
        return delete(favouriteDTO.getFavouriteId());
    }

    public boolean delete(String userId, String videoId) {
        if(ValidationUtil.isNullOrBlank(userId) || ValidationUtil.isNullOrBlank(videoId)) return false;

        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            List<Favourite> favouriteList = em.createQuery("SELECT f FROM Favourite f WHERE f.user.userId = :userId AND f.video.videoId = :videoId", Favourite.class).setParameter("userId", userId).setParameter("videoId", videoId).getResultList();
            if(!favouriteList.isEmpty()) {
                delete(favouriteList.getFirst().getFavouriteId());
                return true;
            } else {
                logger.log(Level.SEVERE, "Favourite with userId " + userId + " and videoId " + videoId + " not found for removal");
            }
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error removing favourite", e);
        }
        return false;
    }
}
