package com.teamchallenge.marketplace.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sellers")
public class Seller extends User {
    @NotEmpty(message = "it can't be empty")
    private String companyName;
    @OneToMany
    private List<Product> products;

}
