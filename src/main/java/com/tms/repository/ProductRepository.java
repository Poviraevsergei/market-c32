package com.tms.repository;

import com.tms.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "INSERT INTO l_users_product (user_id, product_id) VALUES (:userId, :productId)", nativeQuery = true)
    int addProductToUser(Long userId, Long productId);
}
