package com.teamchallenge.marketplace.repositories;

import com.teamchallenge.marketplace.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {
    public Optional<Seller> findById(long id);
    public Optional<Seller> findByEmail(String email);
}
