package com.example.shopproject.serviceTest;

import com.example.shopproject.entity.Product;
import com.example.shopproject.repository.ProductRepository;
import com.example.shopproject.request.CreateProductRequest;
import com.example.shopproject.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    //Test product create
    @Test
    void successfullyCreatedProduct(){
        CreateProductRequest createProductRequest = new CreateProductRequest("TestProducst", "Product description", 100);
        Product product = new Product(createProductRequest.getName(), createProductRequest.getDescription(), createProductRequest.getPrice());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        assertNotNull(productService.createProduct(createProductRequest));
    }

    @Test
    void nameTooShort(){
        CreateProductRequest createProductRequest = new CreateProductRequest("T", "Product description", 100);
        Product product = new Product(createProductRequest.getName(), createProductRequest.getDescription(), createProductRequest.getPrice());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        assertNull(productService.createProduct(createProductRequest));
    }

    @Test
    void descriptionTooShort(){
        CreateProductRequest createProductRequest = new CreateProductRequest("TestProducst", "dsc", 100);
        Product product = new Product(createProductRequest.getName(), createProductRequest.getDescription(), createProductRequest.getPrice());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        assertNull(productService.createProduct(createProductRequest));
    }

    @Test
    void priceNotValid(){
        CreateProductRequest createProductRequest = new CreateProductRequest("TestProducst", "Product description", 0);
        Product product = new Product(createProductRequest.getName(), createProductRequest.getDescription(), createProductRequest.getPrice());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        assertNull(productService.createProduct(createProductRequest));
    }

    //Test product delete
    @Test
    void productDelete(){
        when(productRepository.existsById(any(Long.class))).thenReturn(true);
        assertTrue(productService.deleteProduct(1L));
    }

    @Test
    void productDeleteNotExists(){
        when(productRepository.existsById(any(Long.class))).thenReturn(false);
        assertFalse(productService.deleteProduct(1L));
    }
}
