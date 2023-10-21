package com.teamchallenge.marketplace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends User {
    @NotEmpty(message = "it can't be empty")
    @Size(min = 1, max=100, message = "......")
    private String firstName;
    @NotEmpty(message = "it can't be empty")
    @Size(min = 1, max=100, message = "......")
    private String lastName;
    private LocalDate birthDate;
    @OneToMany
    private List<Product> favourites;
    @OneToMany
    private List<Product> cart;

    public Customer(String email, String password, String phone, String firstName, String lastName, LocalDate birthDate) {
        super(email, password, phone);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.favourites = new ArrayList<>();
        this.cart = new ArrayList<>();
    }
}
