package com.teamchallenge.marketplace.repositories;

import com.teamchallenge.marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findById(long id);

    public Optional<User> findByEmail(String email);

}
