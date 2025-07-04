package com.example.shopproject.serviceTest;

import com.example.shopproject.entity.User;
import com.example.shopproject.repository.UserRepository;
import com.example.shopproject.request.CreateUserRequest;
import com.example.shopproject.service.UserService;
import com.example.shopproject.validation.EmailValidation;
import com.example.shopproject.validation.PasswordValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class UserServiceTests {
    @Mock
    private PasswordValidation passwordValidation;
    @Mock
    private EmailValidation emailValidation;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private CreateUserRequest createUserRequest;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        createUserRequest =  new  CreateUserRequest("username", "Surname", "mail@mail.com", "password123");
    }

    @Test
    void testCreateUserWithValidData() {
        when(passwordValidation.isValidPassword(createUserRequest.getPassword(), createUserRequest.getName(), 3)).thenReturn(true);
        when(emailValidation.isValidEmail(createUserRequest.getEmail())).thenReturn(true);
        User response = userService.createUser(createUserRequest);
        assertNotNull(response);
    }

    @Test
    void testInvalidPassword(){
        when(passwordValidation.isValidPassword(createUserRequest.getPassword(), createUserRequest.getName(), 3)).thenReturn(false);
        when(emailValidation.isValidEmail(createUserRequest.getEmail())).thenReturn(true);
        User response = userService.createUser(createUserRequest);
        assertNull(response);
    }

    @Test
    void testInvalidEmail(){
        when(passwordValidation.isValidPassword(createUserRequest.getPassword(), createUserRequest.getName(), 3)).thenReturn(true);
        when(emailValidation.isValidEmail(createUserRequest.getEmail())).thenReturn(false);
        User response = userService.createUser(createUserRequest);
        assertNull(response);
    }

    @Test
    void testInvalidPasswordAndEmail(){
        when(passwordValidation.isValidPassword(createUserRequest.getPassword(), createUserRequest.getName(), 3)).thenReturn(false);
        when(emailValidation.isValidEmail(createUserRequest.getEmail())).thenReturn(false);
        User response = userService.createUser(createUserRequest);
        assertNull(response);
    }


}
