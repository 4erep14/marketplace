package com.teamchallenge.marketplace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotEmpty(message = "it can't be empty")
    private String email;
    @NotEmpty(message = "it can't be empty")
    private String password;
   // @NotEmpty(message = "it can't be empty")
    private String phone;
    @NotEmpty(message = "it can't be empty")
    @Size(min = 1, max=100, message = "......")
    private String firstName;
    @NotEmpty(message = "it can't be empty")
    @Size(min = 1, max=100, message = "......")
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDate birthDate;
    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;
    @OneToMany
    private List<Product> favourites;
    @OneToMany
    private List<Product> cart;
}
