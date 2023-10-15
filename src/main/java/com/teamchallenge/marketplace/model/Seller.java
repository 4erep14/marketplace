package com.teamchallenge.marketplace.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sellers")
@Getter
@Setter
@NoArgsConstructor
public class Seller extends User {

    private String companyName;
    @OneToMany
    private List<Product> products;

    public Seller(String email, String password, String phone, String companyName) {
        super(email, password, phone);
        this.companyName = companyName;
        this.products = new ArrayList<>();
    }
}
