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

import java.util.ArrayList;
import java.util.List;

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


    //User add TESTS
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


    //User DELETE tests
    @Test
    void testUserDeleteSuccessful(){
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);
        boolean response = userService.deleteUser(userId);
        assertTrue(response);
    }

    @Test
    void testUserDeleteFailedNotExists(){
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);
        boolean response = userService.deleteUser(userId);
        assertFalse(response);
    }

    //Users getAllUsers tests
    @Test
    void getAllUsersSuccessful(){
        List<User> fakeResponse = new ArrayList<>();
        fakeResponse.add(new User("Name", "Surname", "Email", "Password"));
        fakeResponse.add(new User("Name2", "Surname2", "Email2", "Password2"));
        fakeResponse.add(new User("Name3", "Surname3", "Email3", "Password3"));
        when(userRepository.findAll()).thenReturn(fakeResponse);
        assertNotNull(userService.getAllUsers());
    }
}
