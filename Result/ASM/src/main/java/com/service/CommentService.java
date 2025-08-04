package com.service;

import com.dto.CommentDTO;
import com.entity.Comment;
import com.entity.User;
import com.entity.Video;
import com.mapper.CommentMapper;
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
public class CommentService implements Service<CommentDTO, Long>{
    private static final Logger logger = Logger.getLogger(CommentService.class.getName());

    @Override
    public List<CommentDTO> findAll() {
        List<Comment> commentList = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            commentList = em.createQuery("SELECT s FROM Comment s", Comment.class).getResultList();
            logger.info("Fetched all comments: " + commentList.size() + " comments found.");
            return CommentMapper.toDTOList(commentList);
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error fetching comments", e);
            return new ArrayList<>();
        }
    }

    @Override
    public CommentDTO findById(Long commentId) {
        if (commentId == null) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        Comment comment;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            comment = em.find(Comment.class, commentId);
            logger.info("Comment with id " + commentId + (comment != null ? " found." : " not found."));
            return comment != null ? CommentMapper.toDTO(comment) : null;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding comment by ID", e);
            return null;
        }
    }

    // Manual creation method
    public boolean create(String userId, String videoId, String email) {
        return create(new CommentDTO(userId, videoId, email));
    }

    // Object creation method
    @Override
    public boolean create(CommentDTO commentDTO) {
        if (commentDTO == null) {
            throw new IllegalArgumentException("Comment cannot be null");
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            User user = em.find(User.class, commentDTO.getUserId());
            Video video = em.find(Video.class, commentDTO.getVideoId());
            if(user == null) {
                logger.warning("user " + commentDTO.getUserId() + " not found");
                return false;
            }
            if(video == null) {
                logger.warning("video " + commentDTO.getVideoId() + " not found");
                return false;
            }
            Comment comment = CommentMapper.toEntity(commentDTO, user, video);
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(comment);
                tx.commit();
                logger.info("Comment created: " + comment);
                return true;
            } catch (PersistenceException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error creating comment", e);
                return false;
            }
        }
    }

    public boolean update(Long commentId, String userId, String videoId, String email) {
        return update(new CommentDTO(commentId, userId, videoId, email));
    }

    // Object update method
    @Override
    public boolean update(CommentDTO commentDTO) {
        if (commentDTO == null || commentDTO.getCommentId() == null) {
            logger.warning("Comment or Comment ID cannot be null or empty");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                Comment existingComment = em.find(Comment.class, commentDTO.getCommentId());
                if (existingComment != null) {
                    tx.begin();
                    if(!ValidationUtil.isNullOrBlank(commentDTO.getCommentContent())) existingComment.setCommentContent(commentDTO.getCommentContent());

                    User user = em.find(User.class, commentDTO.getUserId());
                    if(user != null) user.addComment(existingComment);

                    Video video = em.find(Video.class, commentDTO.getVideoId());
                    if(video != null) video.addComment(existingComment);

                    tx.commit();
                    logger.info("Comment with id " + commentDTO.getCommentId() + " updated successfully.");
                    return true;
                } else {
                    logger.warning("Comment with id " + commentDTO.getCommentId() + " not found for update.");
                    return false;
                }
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error updating comment", e);
                return false;
            }
        }
    }

    //Manual delete method
    @Override
    public boolean delete(Long commentId) {
        if (commentId == null) {
            logger.warning("ID cannot be null");
            return false;
        }
        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            Comment comment = em.find(Comment.class, commentId);
            if (comment == null) {
                logger.warning("Comment with id " + commentId + " not found. Deletion skipped.");
                return false;
            }

            try {
                tx.begin();
                em.remove(comment);
                new User().removeComment(comment);
                new Video().removeComment(comment);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error deleting comment with id " + commentId, e);
                return false;
            }
        }
    }

    // Object delete method
    public boolean delete(CommentDTO commentDTO) {
        if (commentDTO == null) {
            logger.warning("Comment cannot be null");
            return false;
        }
        return delete(commentDTO.getCommentId());
    }
}
