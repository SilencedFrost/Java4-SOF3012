package com.service;

import com.entity.Author;
import com.entity.Book;
import com.util.EntityManagerUtil;
import com.util.ValidationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookService implements Service<Book, Integer> {
    private static final Logger logger = Logger.getLogger(BookService.class.getName());

    @Override
    public List<Book> findAll() {
        List<Book> bookList = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            bookList = em.createQuery("SELECT r FROM Book r", Book.class).getResultList();
            logger.info("Fetched all authors: " + bookList.size() + " authors found.");
            return bookList;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error fetching authors", e);
            return List.of();
        }
    }

    @Override
    public Book findById(Integer bookId) {
        if (bookId == null) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        Book book = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            book = em.find(Book.class, bookId);
            logger.info("Book with id " + bookId + (book != null ? " found." : " not found."));
            return book;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding book by ID", e);
            return null;
        }
    }

    public boolean create(String bookName, Integer bookPrice, Author author) {
        return create(new Book(bookName, bookPrice, author));
    }

    @Override
    public boolean create(Book book) {
        if(book == null) {
            logger.warning("Book is null, aborting creation");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            Author author = book.getAuthor();
            if(author == null) {
                logger.warning("Category not found");
                return false;
            }
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(book);
                tx.commit();
                logger.info("Book created: " + book);
                return true;
            } catch (PersistenceException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error creating book", e);
                return false;
            }
        }
    }



    @Override
    public boolean update(Book book) {
        if (book == null || book.getBookId() == null) {
            logger.warning("Book or book ID cannot be null or empty");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                Book existingBook = em.find(Book.class, book.getBookId());
                if (existingBook != null) {
                    tx.begin();
                    if (book.getTitle() != null) existingBook.setTitle(book.getTitle());
                    if (book.getAuthor() != null) existingBook.setAuthor(book.getAuthor());
                    tx.commit();
                    logger.info("Book with id " + book.getBookId() + " updated successfully.");
                    return true;
                } else {
                    logger.warning("Book with id " + book.getBookId() + " not found for update.");
                    return false;
                }
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error updating book", e);
                return false;
            }
        }
    }

    @Override
    public boolean delete(Integer bookId) {
        if (bookId == null) {
            logger.warning("ID cannot be null");
            return false;
        }
        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            Book book = em.find(Book.class, bookId);
            if (book == null) {
                logger.warning("Book with id " + bookId + " not found. Deletion skipped.");
                return false;
            }

            try {
                tx.begin();
                em.remove(book);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error deleting user with id " + bookId, e);
                return false;
            }
        }
    }

    public static Book findByBookName(EntityManager em, String bookName) {
        if (ValidationUtil.isNullOrBlank(bookName)) {
            return null;
        }

        try {
            TypedQuery<Book> query = em.createQuery(
                    "SELECT r FROM Book r WHERE r.bookName = :bookName",
                    Book.class
            );
            query.setParameter("bookName", bookName);

            List<Book> books = query.getResultList();
            Book book = books.isEmpty() ? null : books.getFirst();

            logger.info("Book with name '" + bookName + "'" + (book != null ? " found." : " not found."));

            return book;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding book by name: " + bookName, e);
            return null;
        }
    }
}
