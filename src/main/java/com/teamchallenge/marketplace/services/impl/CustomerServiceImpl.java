package com.teamchallenge.marketplace.services.impl;

import com.teamchallenge.marketplace.model.Customer;
import com.teamchallenge.marketplace.repositories.CustomerRepository;
import com.teamchallenge.marketplace.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer create(Customer customer) {
        if(customer !=null) {
            return customerRepository.save(customer);
        } else {
            throw new NullPointerException("Seller cannot be null");
        }
    }

    @Override
    public Customer readById(long id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found")
        );
    }

    @Override
    public Customer readByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User with email " + " not found")
        );
    }

    @Override
    public Customer update(Customer customer) {
        if (customer != null) {
            return customerRepository.save(customer);
        }
        throw new NullPointerException("User cannot be 'null'");
    }

    @Override
    public void delete(long id) { customerRepository.delete(readById(id)); }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
