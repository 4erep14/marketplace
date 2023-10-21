package com.teamchallenge.marketplace.security;

import com.teamchallenge.marketplace.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
@Component
public class AuthProviderIMPL implements AuthenticationProvider {
    private final PersonDetailsService personDetailsService;
    @Autowired
    public AuthProviderIMPL(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name= authentication.getName();
        UserDetails personDetails =personDetailsService.loadUserByUsername(name);

        String password=authentication.getCredentials().toString();
        if (!password.equals(personDetails.getPassword()))
            throw new BadCredentialsException("Incorrect Password");
        return new UsernamePasswordAuthenticationToken(personDetails, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
