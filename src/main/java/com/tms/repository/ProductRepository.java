package com.tms.repository;

import com.tms.model.Product;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductRepository {

    public final EntityManager entityManager;
    private static final Logger log = LoggerFactory.getLogger(ProductRepository.class);

    @Autowired
    public ProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.of(entityManager.find(Product.class, id));
    }

    public Boolean deleteProduct(Long id) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Product.class, id));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public Boolean createProduct(Product product) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(product);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public Optional<Product> updateProduct(Product product) {
        Product productUpdated = null;

        try {
            entityManager.getTransaction().begin();
            productUpdated = entityManager.merge(product);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error(e.getMessage());
        }
        return Optional.of(productUpdated);
    }
}
