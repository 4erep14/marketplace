package com.teamchallenge.marketplace.services.impl;

import com.teamchallenge.marketplace.model.Store;
import com.teamchallenge.marketplace.repositories.StoreRepository;
import com.teamchallenge.marketplace.services.StoreService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public Store create(Store store) {
        if(store !=null) {
            return storeRepository.save(store);
        } else {
            throw new NullPointerException("Seller cannot be null");
        }
    }

    @Override
    public Store readById(long id) {
        return storeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found")
        );
    }

    @Override
    public Store readByOwnerId(long id) {
        return storeRepository.findByOwnerId(id).orElseThrow(
                () -> new EntityNotFoundException("User with email " + " not found")
        );
    }

    @Override
    public Store update(Store store) {
        if (store != null) {
            return storeRepository.save(store);
        }
        throw new NullPointerException("User cannot be 'null'");
    }

    @Override
    public void delete(long id) { storeRepository.delete(readById(id)); }

    @Override
    public List<Store> getAll() {
        return storeRepository.findAll();
    }
}
