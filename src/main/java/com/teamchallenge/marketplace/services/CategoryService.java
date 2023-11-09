package com.teamchallenge.marketplace.services;

import com.teamchallenge.marketplace.model.Category;
import com.teamchallenge.marketplace.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public Category create(Category category) {
        if (category != null) {
            logger.info("Creating category:{}", category);
            return categoryRepository.save(category);
        } else {
            throw new NullPointerException("Category cannot be null");
        }
    }

    public Category readById(long id) {
        logger.info("Retrieving category with ID: {}", id);
        return categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category with id " + id + " not found")
        );
    }

    public Category update(Category category) {
        if (category != null) {
            logger.info("Update category: {}", category);
            return categoryRepository.save(category);
        } else {
            throw new NullPointerException("Category cannot be null");
        }
    }

    public void delete(long id) {
        logger.info("Deleting category with ID: {}", id);
        categoryRepository.delete(readById(id));
    }

    public List<Category> getAll() {
        logger.info("Retrieving all category");
        return categoryRepository.findAll();
    }
}
