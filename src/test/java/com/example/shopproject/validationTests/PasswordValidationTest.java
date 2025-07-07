package com.example.shopproject.validationTests;

import com.example.shopproject.validation.PasswordValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordValidationTest {
    private PasswordValidation passwordValidation;
    private final Integer maxPasswordStr = 5;
    private final String username = "user";

    @BeforeEach
    void setUp() {
        passwordValidation = new PasswordValidation();
    }

    //Password valid
    @Test
    void testFullPasswordStrValid(){
        String password = "Test123!^@";
        assertTrue(passwordValidation.isValidPassword(password, username, maxPasswordStr));
    }

    //Password Strength tests
    @Test
    void  testPasswordStrTooHigh(){
        String password = "Test123!^@";
        Integer passwordStrength = maxPasswordStr + 1;
        assertFalse(passwordValidation.isValidPassword(password, username, passwordStrength));
    }
    @Test
    void passwordStrTooLow(){
        String password = "Test123!^@";
        Integer passwordStrength = -1;
        assertFalse(passwordValidation.isValidPassword(password, username, passwordStrength));
    }

    //Password length tests
    @Test
    void passwordTooShort(){
        String password = "T1#3";
        assertFalse(passwordValidation.isValidPassword(password, username, 0));
    }

    //Password content tests
    @Test
    void passwordNotLongEnoughForFullRating(){
        String password = "Test1!3";
        assertFalse(passwordValidation.isValidPassword(password, username, maxPasswordStr));
    }

    @Test
    void passwordDoesNotContainsSpecial() {
        String password = "Test1234";
        assertFalse(passwordValidation.isValidPassword(password, username, maxPasswordStr));
    }

    @Test
    void passwordContainsUsername(){
        String password = "User123!^@";
        assertFalse(passwordValidation.isValidPassword(password, username, maxPasswordStr));
    }

    @Test
    void passwordDoesNotContainsDigit(){
        String password = "Test!@#$#@";
        assertFalse(passwordValidation.isValidPassword(password, username, maxPasswordStr));
    }

    @Test
    void passwordDoesNotContainsUpperCase(){
        String password = "test123@#@!";
        assertFalse(passwordValidation.isValidPassword(password, username, maxPasswordStr));
    }
}
