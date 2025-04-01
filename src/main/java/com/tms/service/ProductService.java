package com.tms.service;

import com.tms.model.Product;
import com.tms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Boolean createProduct(Product product) {
        return productRepository.createProduct(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.getProductById(id);
    }

    public Optional<Product> updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    public Boolean deleteProduct(Long id) {
        return productRepository.deleteProduct(id);
    }
}

