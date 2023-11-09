package com.teamchallenge.marketplace.services;

import com.teamchallenge.marketplace.exception.NullEntityReferenceException;
import com.teamchallenge.marketplace.model.Store;
import com.teamchallenge.marketplace.repositories.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final Logger logger = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store create(Store store) {
        if(store !=null) {
            logger.info("Creating store:{}", store);
            return storeRepository.save(store);
        } else {
            throw new NullEntityReferenceException("Seller cannot be null");
        }
    }

    public Store readById(long id) {
        logger.info("Retrieving store with ID: {}", id);
        return storeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found")
        );
    }

    public Store readByOwnerId(long id) {
        logger.info("Retrieving store owner: {}", id);
        return storeRepository.findByOwnerId(id).orElseThrow(
                () -> new EntityNotFoundException("User with email " + " not found")
        );
    }

    public Store update(Store store) {
        if (store != null) {
            logger.info("Update user: {}", store);
            return storeRepository.save(store);
        }
        throw new NullEntityReferenceException("User cannot be 'null'");
    }

    public void delete(long id) {
        logger.info("Deleting store with ID: {}", id);
        storeRepository.delete(readById(id));
    }

    public List<Store> getAll() {
        logger.info("Retrieving all stores");
        return storeRepository.findAll();
    }
}
