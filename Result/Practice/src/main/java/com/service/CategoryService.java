package com.service;

import com.dto.CategoryDTO;
import com.entity.Category;
import com.mapper.CategoryMapper;
import com.util.EntityManagerUtil;
import com.util.ValidationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryService implements Service<CategoryDTO, String> {
    private static final Logger logger = Logger.getLogger(CategoryService.class.getName());

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categoryList = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            categoryList = em.createQuery("SELECT r FROM Category r", Category.class).getResultList();
            logger.info("Fetched all users: " + categoryList.size() + " users found.");
            return CategoryMapper.toDTOList(categoryList);
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error fetching users", e);
            return List.of();
        }
    }

    @Override
    public CategoryDTO findById(String categoryId) {
        if (categoryId == null) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        Category category = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            category = em.find(Category.class, categoryId);
            logger.info("Category with id " + categoryId + (category != null ? " found." : " not found."));
            return category != null ? CategoryMapper.toDTO(category) : null;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding category by ID", e);
            return null;
        }
    }

    public boolean create(String categoryId, String categoryName) {
        return create(new CategoryDTO(categoryId, categoryName));
    }

    @Override
    public boolean create(CategoryDTO categoryDTO) {
        if(categoryDTO == null) {
            logger.warning("Category is null, aborting creation");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            Category category = CategoryMapper.toEntity(categoryDTO);

            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(category);
                tx.commit();
                logger.info("Category created: " + category);
                return true;
            } catch (PersistenceException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error creating category", e);
                return false;
            }
        }
    }

    @Override
    public boolean update(CategoryDTO categoryDTO) {
        if (categoryDTO == null || categoryDTO.getCategoryId() == null) {
            logger.warning("Category or category ID cannot be null or empty");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                Category existingCategory = em.find(Category.class, categoryDTO.getCategoryId());
                if (existingCategory != null) {
                    tx.begin();
                    if (categoryDTO.getCategoryName() != null) existingCategory.setCategoryName(categoryDTO.getCategoryName());
                    tx.commit();
                    logger.info("Category with id " + categoryDTO.getCategoryId() + " updated successfully.");
                    return true;
                } else {
                    logger.warning("Category with id " + categoryDTO.getCategoryId() + " not found for update.");
                    return false;
                }
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error updating category", e);
                return false;
            }
        }
    }

    @Override
    public boolean delete(String categoryId) {
        if (categoryId == null) {
            logger.warning("ID cannot be null");
            return false;
        }
        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            Category category = em.find(Category.class, categoryId);
            if (category == null) {
                logger.warning("Category with id " + categoryId + " not found. Deletion skipped.");
                return false;
            }

            try {
                tx.begin();
                em.remove(category);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error deleting user with id " + categoryId, e);
                return false;
            }
        }
    }

    public static Category findByCategoryName(EntityManager em, String categoryName) {
        if (ValidationUtil.isNullOrBlank(categoryName)) {
            return null;
        }

        try {
            TypedQuery<Category> query = em.createQuery(
                    "SELECT r FROM Category r WHERE r.categoryName = :categoryName",
                    Category.class
            );
            query.setParameter("categoryName", categoryName);

            List<Category> categories = query.getResultList();
            Category category = categories.isEmpty() ? null : categories.getFirst();

            logger.info("Category with name '" + categoryName + "'" + (category != null ? " found." : " not found."));

            return category;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding category by name: " + categoryName, e);
            return null;
        }
    }
}
