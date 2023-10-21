package com.teamchallenge.marketplace.services;

import com.teamchallenge.marketplace.model.User;
import com.teamchallenge.marketplace.repositories.PeopleRepository;
import com.teamchallenge.marketplace.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private PeopleRepository peopleRepository;
    @Autowired

    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user= peopleRepository.findByUserName(s);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found!");
        return new PersonDetails(user.get());
    }
}
