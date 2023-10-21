package com.teamchallenge.marketplace.security;

import com.teamchallenge.marketplace.services.CustomerService;
import com.teamchallenge.marketplace.services.PersonDetailsService;
import com.teamchallenge.marketplace.services.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomerAuthProvider implements AuthenticationProvider {
    private final CustomerServiceImpl customerService;
    @Autowired
    public CustomerAuthProvider(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name= authentication.getName();
        SecurityCustomer customer = SecurityCustomer.fromCustomer(customerService.readByEmail(name));

        String password=authentication.getCredentials().toString();
        if (!password.equals(customer.getPassword()))
            throw new BadCredentialsException("Incorrect Password");
        return new UsernamePasswordAuthenticationToken(customer, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
