package com.teamchallenge.marketplace.security;

import com.teamchallenge.marketplace.model.Customer;
import com.teamchallenge.marketplace.model.User;

public class SecurityCustomer extends SecurityUser {
    public SecurityCustomer(User user) {
        super(user);
    }

    public static SecurityCustomer fromCustomer(Customer customer) {
        return new SecurityCustomer(customer);
    }
}
