package com.example.shopproject.controller;

import com.example.shopproject.entity.ERole;
import com.example.shopproject.entity.User;
import com.example.shopproject.repository.ProductRepository;
import com.example.shopproject.request.CreateProductRequest;
import com.example.shopproject.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:63342")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRequest createProductRequest){
        return productService.createProduct(createProductRequest);
    }
}
