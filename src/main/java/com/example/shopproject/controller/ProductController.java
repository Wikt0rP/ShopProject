package com.example.shopproject.controller;

import com.example.shopproject.request.CreateProductRequest;
import com.example.shopproject.service.ProductService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts(){
        return null;
    }
}
