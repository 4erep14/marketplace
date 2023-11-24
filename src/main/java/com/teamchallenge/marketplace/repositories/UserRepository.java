package com.teamchallenge.marketplace.repositories;

import com.teamchallenge.marketplace.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndProviderId(String email, String id);
    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.isEnabled = TRUE WHERE a.email = ?1")
    int enableUser(String email);

}
