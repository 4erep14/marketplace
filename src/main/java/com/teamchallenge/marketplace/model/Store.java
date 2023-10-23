package com.teamchallenge.marketplace.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue
    private long id;
    @NotEmpty(message = "it can't be empty")
    private String companyName;
    @OneToMany
    private List<Product> products;
    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
