package com.teamchallenge.marketplace.services;

import com.teamchallenge.marketplace.model.Category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    Category readById(long id);
    Category update(Category category);
    void delete(long id);
    List<Category> getAll();
}
