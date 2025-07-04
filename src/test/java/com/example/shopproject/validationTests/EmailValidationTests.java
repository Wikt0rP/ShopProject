package com.example.shopproject.validationTests;

import com.example.shopproject.repository.UserRepository;
import com.example.shopproject.validation.EmailValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.Mockito.when;

public class EmailValidationTests {
    @Mock
    UserRepository mockUserRepo;
    @InjectMocks
    private EmailValidation emailValidation;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidMailUserExists(){
        String mail = "test@mail.com";
        when(mockUserRepo.existsByEmail(mail)).thenReturn(true);
        boolean result = emailValidation.isValidEmail(mail);
        assertFalse(result);
    }
}
