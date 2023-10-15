package com.teamchallenge.marketplace.model;

import jakarta.persistence.*;
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
    private String name;
    private String description;
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
