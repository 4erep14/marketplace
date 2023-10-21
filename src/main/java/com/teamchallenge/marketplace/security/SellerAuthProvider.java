package com.teamchallenge.marketplace.security;

import com.teamchallenge.marketplace.services.impl.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SellerAuthProvider implements AuthenticationProvider {

    private final SellerServiceImpl sellerService;

    @Autowired
    public SellerAuthProvider(SellerServiceImpl sellerService) {
        this.sellerService = sellerService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name= authentication.getName();
        SecuritySeller seller = SecuritySeller.fromSeller(sellerService.readByEmail(name));

        String password=authentication.getCredentials().toString();
        if (!password.equals(seller.getPassword()))
            throw new BadCredentialsException("Incorrect Password");
        return new UsernamePasswordAuthenticationToken(seller, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
