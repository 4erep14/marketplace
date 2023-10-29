package com.teamchallenge.marketplace.services.impl;

import com.teamchallenge.marketplace.model.Category;
import com.teamchallenge.marketplace.repositories.CategoryRepository;
import com.teamchallenge.marketplace.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        if (category != null) {
            return categoryRepository.save(category);
        } else {
            throw new NullPointerException("Category cannot be null");
        }
    }

    @Override
    public Category readById(long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category with id " + id + " not found")
        );
    }

    @Override
    public Category update(Category category) {
        if (category != null) {
            return categoryRepository.save(category);
        } else {
            throw new NullPointerException("Category cannot be null");
        }
    }

    @Override
    public void delete(long id) {
        categoryRepository.delete(readById(id));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
