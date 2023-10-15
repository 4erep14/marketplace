package com.teamchallenge.marketplace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotEmpty(message = "it can't be empty")
    @Size(min = 5, max=100, message = "......")
    private String name;
    @NotEmpty(message = "it can't be empty")
    private String description;
    @NotEmpty(message = "it can't be empty")
    private int price;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Seller seller;

    public Product(String name, String description, int price, Category category, Seller seller) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.seller = seller;
    }
}
