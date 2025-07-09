package com.example.shopproject.service;

import com.example.shopproject.entity.Product;
import com.example.shopproject.entity.User;
import com.example.shopproject.exception.UserNotAuthorizedException;
import com.example.shopproject.exception.UserNotFoundException;
import com.example.shopproject.repository.ProductRepository;
import com.example.shopproject.request.CreateProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service 
public class ProductService {
    private final ProductRepository productRepository;
    private final CurrentUserService currentUserService;

    public ProductService(ProductRepository productRepository, CurrentUserService currentUserService) {
        this.productRepository = productRepository;
        this.currentUserService = currentUserService;
    }

    public ResponseEntity<?> createProduct(CreateProductRequest createProductRequest){
        if(!isDataValid(createProductRequest)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }

        User user;
        try {
            user = currentUserService.getUserFromAuth();
        }catch (UserNotAuthorizedException authEx){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in");
        }catch (UserNotFoundException userEx){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Product product = new Product(createProductRequest.getName(),
                createProductRequest.getDescription(), createProductRequest.getPrice(), user);
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product.getName() + " created");
    }

    public boolean deleteProduct(Long id){
        if(!productRepository.existsById(id)){
            return false;
        }

        productRepository.deleteById(id);
        return true;
    }

    private boolean isDataValid(CreateProductRequest createProductRequest) {
        if(createProductRequest.getName() == null || createProductRequest.getName().length() < 4) {
            return false;
        } else if(createProductRequest.getDescription() == null || createProductRequest.getDescription().length() < 10){
            return false;
        }else return createProductRequest.getPrice() != null && createProductRequest.getPrice() >= 1;
    }
}
