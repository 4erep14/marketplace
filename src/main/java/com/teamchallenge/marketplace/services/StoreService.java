package com.teamchallenge.marketplace.services;

import com.teamchallenge.marketplace.model.Store;
import com.teamchallenge.marketplace.repositories.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store create(Store store) {
        if(store !=null) {
            return storeRepository.save(store);
        } else {
            throw new NullPointerException("Seller cannot be null");
        }
    }

    public Store readById(long id) {
        return storeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found")
        );
    }

    public Store readByOwnerId(long id) {
        return storeRepository.findByOwnerId(id).orElseThrow(
                () -> new EntityNotFoundException("User with email " + " not found")
        );
    }

    public Store update(Store store) {
        if (store != null) {
            return storeRepository.save(store);
        }
        throw new NullPointerException("User cannot be 'null'");
    }

    public void delete(long id) { storeRepository.delete(readById(id)); }

    public List<Store> getAll() {
        return storeRepository.findAll();
    }
}
