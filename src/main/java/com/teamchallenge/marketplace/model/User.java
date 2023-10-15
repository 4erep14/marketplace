package com.teamchallenge.marketplace.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String email;
    private String password;
    private String phone;

    public User(String email, String password, String phone) {
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}
