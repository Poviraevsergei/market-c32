package com.tms.repository;

import com.tms.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.tms.config.SQLQuery.ADD_PRODUCT_BY_USER;

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

    public Boolean addProductByUser(Long userId, Long productId) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery(ADD_PRODUCT_BY_USER);
            query.setParameter(1, productId);
            query.setParameter(2, userId);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error(e.getMessage());
        }
        return false;
    }
    
/*    public Boolean addProductByUser(Long userId, Long productId) {
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class, userId);
            Product product = entityManager.find(Product.class, productId); 
            user.getProducts().add(product);
            entityManager.merge(user);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error(e.getMessage());
        }
        return false;
    }*/

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
