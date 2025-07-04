package com.example.shopproject.validationTests;

import com.example.shopproject.repository.UserRepository;
import com.example.shopproject.validation.EmailValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class EmailValidationTests {
    @Mock
    UserRepository mockUserRepo;
    @InjectMocks
    private EmailValidation emailValidation;
    private final String goodMail = "test@mail.com";
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidMailUserExists(){
        when(mockUserRepo.existsByEmail(goodMail)).thenReturn(true);
        assertFalse(emailValidation.isValidEmail(goodMail));
    }

    @Test
    void testValidUniqueMail(){
        when(mockUserRepo.existsByEmail(goodMail)).thenReturn(false);
        assertTrue(emailValidation.isValidEmail(goodMail));
    }

    @Test
    void testInvalidMailSimpleText(){
        when(mockUserRepo.existsByEmail(goodMail)).thenReturn(false);
        String mail = "simpleText";
        assertFalse(emailValidation.isValidEmail(mail));
    }

    @Test
    void testInvalidMailCom(){
        when(mockUserRepo.existsByEmail(goodMail)).thenReturn(false);
        String mail = "simpleText.com";
        assertFalse(emailValidation.isValidEmail(mail));
    }
    @Test
    void testInvalidMailAt(){
        when(mockUserRepo.existsByEmail(goodMail)).thenReturn(false);
        String mail = "simpleText@com";
        assertFalse(emailValidation.isValidEmail(mail));
    }
}
