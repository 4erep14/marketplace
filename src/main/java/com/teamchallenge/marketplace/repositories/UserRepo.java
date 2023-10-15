package com.teamchallenge.marketplace.repositories;

import com.teamchallenge.marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findById(String userName);
}
