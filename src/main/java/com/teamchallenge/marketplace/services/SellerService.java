package com.teamchallenge.marketplace.services;

import com.teamchallenge.marketplace.model.Seller;

import java.util.List;

public interface SellerService {

    Seller create(Seller seller);
    Seller readById(long id);
    Seller readByEmail(String email);
    Seller update(Seller seller);
    void delete(long id);
    List<Seller> getAll();
}
