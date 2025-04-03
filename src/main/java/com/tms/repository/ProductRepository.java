package com.tms.repository;

import com.tms.model.Product;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.tms.config.SQLQuery.ADD_PRODUCT_BY_USER;

@Repository
public class ProductRepository {

    public final Session session;
    private static final Logger log = LoggerFactory.getLogger(ProductRepository.class);

    @Autowired
    public ProductRepository(Session session) {
        this.session = session;
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.of(session.find(Product.class, id));
    }

    public Boolean deleteProduct(Long id) {
        try {
            session.getTransaction().begin();
            session.remove(session.find(Product.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public Boolean createProduct(Product product) {
        try {
            session.getTransaction().begin();
            session.persist(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public Boolean addProductByUser(Long userId, Long productId) {
        try {
            session.getTransaction().begin();
            Query query = session.createNativeQuery(ADD_PRODUCT_BY_USER);
            query.setParameter(1, productId);
            query.setParameter(2, userId);
            query.executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(e.getMessage());
        }
        return false;
    }

    public Optional<Product> updateProduct(Product product) {
        Product productUpdated = null;

        try {
            session.getTransaction().begin();
            productUpdated = session.merge(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(e.getMessage());
        }
        return Optional.of(productUpdated);
    }
}
