package com.teamchallenge.marketplace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
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
    @Enumerated(EnumType.STRING)
    private Role role;
}
