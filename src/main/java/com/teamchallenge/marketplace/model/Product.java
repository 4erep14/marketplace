package com.teamchallenge.marketplace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotEmpty(message = "it can't be empty")
    @Size(min = 5, max=100, message = "......")
    private String name;
    @NotEmpty(message = "it can't be empty")
    @Size(min=60, message = "it should not be less than 60 symbols")
    private String description;
    @NotEmpty(message = "it can't be empty")
    private int price;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Store store;
}
