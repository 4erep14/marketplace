package com.teamchallenge.marketplace.services;

import com.teamchallenge.marketplace.model.Product;

import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product readById(long id);
    Product update(Product product);
    void delete(long id);
    List<Product> getAllFromCategoryId(long id);
    List<Product> getAll();
}
