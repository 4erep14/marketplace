package com.teamchallenge.marketplace.repositories;

import com.teamchallenge.marketplace.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Optional<Customer> findById(long id);

    public Optional<Customer> findByEmail(String email);

}
