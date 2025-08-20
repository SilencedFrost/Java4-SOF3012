package com.service;

import com.entity.Author;
import com.util.EntityManagerUtil;
import com.util.ValidationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorService implements Service<Author, String> {
    private static final Logger logger = Logger.getLogger(AuthorService.class.getName());

    @Override
    public List<Author> findAll() {
        List<Author> authorList;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            authorList = em.createQuery("SELECT r FROM Author r", Author.class).getResultList();
            logger.info("Fetched all authors: " + authorList.size() + " authorss found.");
            return authorList;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error fetching authors", e);
            return List.of();
        }
    }

    @Override
    public Author findById(String authorId) {
        if (authorId == null) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        Author author;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            author = em.find(Author.class, authorId);
            logger.info("Author with id " + authorId + (author != null ? " found." : " not found."));
            return author;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding author by ID", e);
            return null;
        }
    }

    public boolean create(String authorId, String authorName) {
        return create(new Author(authorId, authorName));
    }

    @Override
    public boolean create(Author author) {
        if(author == null) {
            logger.warning("Author is null, aborting creation");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(author);
                tx.commit();
                logger.info("Author created: " + author);
                return true;
            } catch (PersistenceException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error creating author", e);
                return false;
            }
        }
    }

    @Override
    public boolean update(Author author) {
        if (author == null || author.getAuthorId() == null) {
            logger.warning("Author or author ID cannot be null or empty");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                Author existingAuthor = em.find(Author.class, author.getAuthorId());
                if (existingAuthor != null) {
                    tx.begin();
                    if (author.getAuthorName() != null) existingAuthor.setAuthorName(author.getAuthorName());
                    tx.commit();
                    logger.info("Author with id " + author.getAuthorId() + " updated successfully.");
                    return true;
                } else {
                    logger.warning("Author with id " + author.getAuthorId() + " not found for update.");
                    return false;
                }
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error updating author", e);
                return false;
            }
        }
    }

    @Override
    public boolean delete(String authorId) {
        if (authorId == null) {
            logger.warning("ID cannot be null");
            return false;
        }
        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            Author author = em.find(Author.class, authorId);
            if (author == null) {
                logger.warning("Author with id " + authorId + " not found. Deletion skipped.");
                return false;
            }

            try {
                tx.begin();
                em.remove(author);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error deleting user with id " + authorId, e);
                return false;
            }
        }
    }

    public static Author findByAuthorName(EntityManager em, String authorName) {
        if (ValidationUtil.isNullOrBlank(authorName)) {
            return null;
        }

        try {
            TypedQuery<Author> query = em.createQuery(
                    "SELECT r FROM Author r WHERE r.authorName = :authorName",
                    Author.class
            );
            query.setParameter("authorName", authorName);

            List<Author> authors = query.getResultList();
            Author author = authors.isEmpty() ? null : authors.getFirst();

            logger.info("Author with name '" + authorName + "'" + (author != null ? " found." : " not found."));

            return author;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding author by name: " + authorName, e);
            return null;
        }
    }
}
