package com.teamchallenge.marketplace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public  class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;
    @NotEmpty(message = "it can't be empty")
    private String email;
    @NotEmpty(message = "it can't be empty")
    private String password;
    @NotEmpty(message = "it can't be empty")
    private String phone;

    public User(String email, String password, String phone) {
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}
