package com.teamchallenge.marketplace.services;

import com.teamchallenge.marketplace.exception.NullEntityReferenceException;
import com.teamchallenge.marketplace.model.Category;
import com.teamchallenge.marketplace.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category create(Category category) {
        if (category != null) {
            return categoryRepository.save(category);
        } else {
            throw new NullEntityReferenceException("Category cannot be null");
        }
    }

    public Category readById(long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category with id " + id + " not found")
        );
    }

    public Category update(Category category) {
        if (category != null) {
            return categoryRepository.save(category);
        } else {
            throw new NullEntityReferenceException("Category cannot be null");
        }
    }

    public void delete(long id) {
        categoryRepository.delete(readById(id));
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
