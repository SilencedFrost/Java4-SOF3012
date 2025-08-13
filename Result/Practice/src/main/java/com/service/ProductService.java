package com.service;

import com.dto.InboundProductDTO;
import com.dto.ProductDTO;
import com.dto.UpdateProductDTO;
import com.entity.Category;
import com.entity.Product;
import com.mapper.ProductMapper;
import com.util.EntityManagerUtil;
import com.util.ValidationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductService implements Service<ProductDTO, Integer> {
    private static final Logger logger = Logger.getLogger(ProductService.class.getName());

    @Override
    public List<ProductDTO> findAll() {
        List<Product> productList = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            productList = em.createQuery("SELECT r FROM Product r", Product.class).getResultList();
            logger.info("Fetched all users: " + productList.size() + " users found.");
            return ProductMapper.toDTOList(productList);
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error fetching users", e);
            return List.of();
        }
    }

    @Override
    public ProductDTO findById(Integer productId) {
        if (productId == null) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        Product product = null;
        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            product = em.find(Product.class, productId);
            logger.info("Product with id " + productId + (product != null ? " found." : " not found."));
            return product != null ? ProductMapper.toDTO(product) : null;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding product by ID", e);
            return null;
        }
    }

    public boolean create(String productName, Integer productPrice, String categoryId) {
        return create(new InboundProductDTO(productName, productPrice, categoryId));
    }

    @Override
    public boolean create(ProductDTO productDTO) {
        if(productDTO == null) {
            logger.warning("Product is null, aborting creation");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            Category category = CategoryService.findByCategoryName(em, productDTO.getCategoryId());
            if(category == null) {
                logger.warning("Category not found");
                return false;
            }
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Product product = ProductMapper.toEntity((InboundProductDTO) productDTO, category);
                em.persist(product);
                tx.commit();
                logger.info("Product created: " + product);
                return true;
            } catch (PersistenceException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error creating product", e);
                return false;
            }
        }
    }

    @Override
    public boolean update(ProductDTO productDTO) {
        UpdateProductDTO updateProductDTO = (UpdateProductDTO) productDTO;
        if (updateProductDTO == null || updateProductDTO.getProductId() == null) {
            logger.warning("Product or product ID cannot be null or empty");
            return false;
        }

        try (EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                Product existingProduct = em.find(Product.class, updateProductDTO.getProductId());
                if (existingProduct != null) {
                    tx.begin();
                    if (updateProductDTO.getProductName() != null) existingProduct.setProductName(updateProductDTO.getProductName());
                    tx.commit();
                    logger.info("Product with id " + updateProductDTO.getProductId() + " updated successfully.");
                    return true;
                } else {
                    logger.warning("Product with id " + updateProductDTO.getProductId() + " not found for update.");
                    return false;
                }
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error updating product", e);
                return false;
            }
        }
    }

    @Override
    public boolean delete(Integer productId) {
        if (productId == null) {
            logger.warning("ID cannot be null");
            return false;
        }
        try(EntityManager em = EntityManagerUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            Product product = em.find(Product.class, productId);
            if (product == null) {
                logger.warning("Product with id " + productId + " not found. Deletion skipped.");
                return false;
            }

            try {
                tx.begin();
                em.remove(product);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                logger.log(Level.SEVERE, "Error deleting user with id " + productId, e);
                return false;
            }
        }
    }

    public static Product findByProductName(EntityManager em, String productName) {
        if (ValidationUtil.isNullOrBlank(productName)) {
            return null;
        }

        try {
            TypedQuery<Product> query = em.createQuery(
                    "SELECT r FROM Product r WHERE r.productName = :productName",
                    Product.class
            );
            query.setParameter("productName", productName);

            List<Product> products = query.getResultList();
            Product product = products.isEmpty() ? null : products.getFirst();

            logger.info("Product with name '" + productName + "'" + (product != null ? " found." : " not found."));

            return product;
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error finding product by name: " + productName, e);
            return null;
        }
    }
}
