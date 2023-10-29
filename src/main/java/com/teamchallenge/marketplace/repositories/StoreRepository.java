package com.teamchallenge.marketplace.repositories;

import com.teamchallenge.marketplace.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    public Optional<Store> findByOwnerId(long id);
}
