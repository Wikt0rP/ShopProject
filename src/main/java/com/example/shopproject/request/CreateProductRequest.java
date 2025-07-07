package com.example.shopproject.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateProductRequest {
    private String name;
    private String description;
    private Integer price;
}
