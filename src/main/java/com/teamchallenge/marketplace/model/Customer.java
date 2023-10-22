package com.teamchallenge.marketplace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
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
}
