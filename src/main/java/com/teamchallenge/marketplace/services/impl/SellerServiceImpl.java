package com.teamchallenge.marketplace.services.impl;

import com.teamchallenge.marketplace.model.Seller;
import com.teamchallenge.marketplace.repositories.SellerRepository;
import com.teamchallenge.marketplace.services.SellerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    public Seller create(Seller seller) {
        if(seller !=null) {
            return sellerRepository.save(seller);
        } else {
            throw new NullPointerException("Seller cannot be null");
        }
    }

    @Override
    public Seller readById(long id) {
        return sellerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found")
        );
    }

    @Override
    public Seller readByEmail(String email) {
        return sellerRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User with email " + " not found")
        );
    }

    @Override
    public Seller update(Seller seller) {
        if (seller != null) {
            return sellerRepository.save(seller);
        }
        throw new NullPointerException("User cannot be 'null'");
    }

    @Override
    public void delete(long id) { sellerRepository.delete(readById(id)); }

    @Override
    public List<Seller> getAll() {
        return sellerRepository.findAll();
    }
}
