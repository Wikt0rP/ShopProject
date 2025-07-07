package com.example.shopproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer price;

    @ManyToOne
    private User addedBy;

    public Product(String name, String description, Integer price, User addedBy){
        this.name = name;
        this.description = description;
        this.price = price;
        this.addedBy = addedBy;
    }
}
