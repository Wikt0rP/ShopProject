package com.example.shopproject.service;

import com.example.shopproject.entity.Product;
import com.example.shopproject.repository.ProductRepository;
import com.example.shopproject.request.CreateProductRequest;
import org.springframework.stereotype.Service;

@Service 
public class ProductService {
    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    
    public Product createProduct(CreateProductRequest createProductRequest){
        if(!isDataValid(createProductRequest)){
            return null;
        }

        Product product = new Product(createProductRequest.getName(),
                createProductRequest.getDescription(), createProductRequest.getPrice());
        productRepository.save(product);
        return product;
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
