package com.teamchallenge.marketplace.services;


import com.teamchallenge.marketplace.model.Customer;
import com.teamchallenge.marketplace.model.Seller;

import java.util.List;

public interface CustomerService {
    Customer create(Customer seller);
    Customer readById(long id);
    Customer readByEmail(String email);
    Customer update(Customer customer);
    void delete(long id);
    List<Customer> getAll();
}
