package com.example.shopproject.serviceTest;

import com.example.shopproject.entity.ERole;
import com.example.shopproject.entity.Role;
import com.example.shopproject.entity.User;
import com.example.shopproject.repository.RoleRepository;
import com.example.shopproject.repository.UserRepository;
import com.example.shopproject.request.CreateUserRequest;
import com.example.shopproject.security.PasswordEncoderConfig;
import com.example.shopproject.service.RoleService;
import com.example.shopproject.service.UserService;
import com.example.shopproject.validation.EmailValidation;
import com.example.shopproject.validation.PasswordValidation;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class UserServiceTests {
    @Mock
    private PasswordValidation passwordValidation;
    @Mock
    private EmailValidation emailValidation;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoderConfig passwordEncoderConfig;
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
        when(roleRepository.findRoleByName(any(ERole.class)))
                .thenReturn(Optional.of(new Role(ERole.ROLE_USER)));
        when(roleService.tryCreateRoleUser()).thenReturn(new Role(ERole.ROLE_USER));
        when(passwordEncoderConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());

        ResponseEntity<?> response = userService.createUser(createUserRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testInvalidPassword(){
        when(passwordValidation.isValidPassword(createUserRequest.getPassword(), createUserRequest.getName(), 3)).thenReturn(false);
        when(emailValidation.isValidEmail(createUserRequest.getEmail())).thenReturn(true);
        ResponseEntity<?> response = userService.createUser(createUserRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testInvalidEmail(){
        when(passwordValidation.isValidPassword(createUserRequest.getPassword(), createUserRequest.getName(), 3)).thenReturn(true);
        when(emailValidation.isValidEmail(createUserRequest.getEmail())).thenReturn(false);
        ResponseEntity<?> response = userService.createUser(createUserRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testInvalidPasswordAndEmail(){
        when(passwordValidation.isValidPassword(createUserRequest.getPassword(), createUserRequest.getName(), 3)).thenReturn(false);
        when(emailValidation.isValidEmail(createUserRequest.getEmail())).thenReturn(false);
        ResponseEntity<?> response = userService.createUser(createUserRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
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
        Role role = new Role(ERole.ROLE_USER);
        fakeResponse.add(new User("Name", "Surname", "Email", "Password", role));
        fakeResponse.add(new User("Name2", "Surname2", "Email2", "Password2", role));
        fakeResponse.add(new User("Name3", "Surname3", "Email3", "Password3", role));
        when(userRepository.findAll()).thenReturn(fakeResponse);
        assertNotNull(userService.getAllUsers());
    }
}
