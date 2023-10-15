package com.teamchallenge.marketplace.services;

import com.teamchallenge.marketplace.model.User;
import com.teamchallenge.marketplace.repositories.UserRepo;
import com.teamchallenge.marketplace.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private UserRepo userRepo;
    @Autowired

    public PersonDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional= userRepo.findById(username);
        if (userOptional.isEmpty())
            throw new UsernameNotFoundException("User not found!");
        return new PersonDetails(userOptional.get());
    }
}
