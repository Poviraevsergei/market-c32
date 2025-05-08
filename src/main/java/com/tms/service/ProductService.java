package com.tms.service;

import com.tms.model.Product;
import com.tms.model.User;
import com.tms.repository.ProductRepository;
import com.tms.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Boolean createProduct(Product product) {
        try {
            productRepository.save(product);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Boolean addProductByUser(Long userId, Long productId) {
        return productRepository.addProductToUser(userId, productId) > 0;
    }

    public Optional<Product> updateProduct(Product product) {
        return Optional.of(productRepository.save(product));
    }

    public Boolean deleteProduct(Long id) {
        productRepository.deleteById(id);
        return !productRepository.existsById(id);
    }
    
    public List<Product> getAllProductsWithSorting(String field) {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    public Page<Product> getProductsWithPagination(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }
}

