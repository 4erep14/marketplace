package com.teamchallenge.marketplace.services;

import com.teamchallenge.marketplace.model.Product;
import com.teamchallenge.marketplace.repositories.CategoryRepository;
import com.teamchallenge.marketplace.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public Product create(Product product) {
        if (product != null) {
            logger.info("Creating product:{}", product);
            return productRepository.save(product);
        } else {
            throw new NullPointerException("Product cannot be null");
        }
    }

    public Product readById(long id) {
        logger.info("Retrieving product with ID: {}", id);
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product with id " + id + " not found")
        );
    }

    public Product update(Product product) {
        if (product != null) {
            logger.info("Update product: {}", product);
            return productRepository.save(product);
        } else {
            throw new NullPointerException("Product cannot be null");
        }
    }

    public void delete(long id) {
        logger.info("Deleting product with ID: {}", id);
        productRepository.delete(readById(id));
    }

    public List<Product> getAllFromCategoryId(long id) {
        logger.info("Retrieving all products from category with ID:{}", id);
        return productRepository.findAllByCategoryId(id);
    }

    public List<Product> getAll() {
        logger.info("Retrieving all products");
        return productRepository.findAll();
    }
}
