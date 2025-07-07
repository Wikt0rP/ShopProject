package com.example.shopproject.serviceTest;

import com.example.shopproject.entity.ERole;
import com.example.shopproject.entity.Product;
import com.example.shopproject.entity.Role;
import com.example.shopproject.entity.User;
import com.example.shopproject.repository.ProductRepository;
import com.example.shopproject.request.CreateProductRequest;
import com.example.shopproject.service.CurrentUserService;
import com.example.shopproject.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CurrentUserService currentUserService;
    @InjectMocks
    private ProductService productService;
    private User exampleUser;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        exampleUser = new User("User", "user", "User@user.com", "password",new Role(ERole.ROLE_ADMIN));
    }


    //Test product create
    @Test
    void successfullyCreatedProduct(){
        CreateProductRequest createProductRequest = new CreateProductRequest("TestProducst", "Product description", 100);
        when(currentUserService.getUserFromAuth()).thenReturn(exampleUser);

        assertEquals(HttpStatus.CREATED, productService.createProduct(createProductRequest).getStatusCode());
    }

    @Test
    void nameTooShort(){
        CreateProductRequest createProductRequest = new CreateProductRequest("T", "Product description", 100);
        assertEquals(HttpStatus.BAD_REQUEST, productService.createProduct(createProductRequest).getStatusCode());
    }

    @Test
    void descriptionTooShort(){
        CreateProductRequest createProductRequest = new CreateProductRequest("TestProducst", "dsc", 100);
        assertEquals(HttpStatus.BAD_REQUEST, productService.createProduct(createProductRequest).getStatusCode());
    }

    @Test
    void priceNotValid(){
        CreateProductRequest createProductRequest = new CreateProductRequest("TestProducst", "Product description", 0);
        assertEquals(HttpStatus.BAD_REQUEST, productService.createProduct(createProductRequest).getStatusCode());
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
