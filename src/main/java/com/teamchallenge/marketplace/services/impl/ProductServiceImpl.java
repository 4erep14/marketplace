package com.teamchallenge.marketplace.services.impl;

import com.teamchallenge.marketplace.model.Product;
import com.teamchallenge.marketplace.repositories.CategoryRepository;
import com.teamchallenge.marketplace.repositories.ProductRepository;
import com.teamchallenge.marketplace.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product create(Product product) {
        if (product != null) {
            return productRepository.save(product);
        } else {
            throw new NullPointerException("Product cannot be null");
        }
    }

    @Override
    public Product readById(long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product with id " + id + " not found")
        );
    }

    @Override
    public Product update(Product product) {
        if (product != null) {
            return productRepository.save(product);
        } else {
            throw new NullPointerException("Product cannot be null");
        }
    }

    @Override
    public void delete(long id) {
        productRepository.delete(readById(id));
    }

    @Override
    public List<Product> getAllFromCategoryId(long id) {
        return productRepository.findAllByCategoryId(id);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
