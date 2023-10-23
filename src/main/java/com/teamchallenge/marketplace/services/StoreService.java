package com.teamchallenge.marketplace.services;

import com.teamchallenge.marketplace.model.Store;

import java.util.List;

public interface StoreService {

    Store create(Store store);
    Store readById(long id);
    Store readByOwnerId(long id);
    Store update(Store store);
    void delete(long id);
    List<Store> getAll();
}
