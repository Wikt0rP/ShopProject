package com.example.shopproject.validation;

import com.example.shopproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidation {

    @Autowired
    private UserRepository userRepository;

    /**
     * Checks if email is valid and unique
     * @return true if email is valid and unique
     */
    public boolean isValidEmail(String email){
        boolean isEmail, isUnique;
        if(isEmailEmpty(email)){
            return false;
        }

        isEmail = isEmailValid(email);
        isUnique = !userRepository.existsByEmail(email);

        return isEmail && isUnique;
    }


    private boolean isEmailEmpty(String email) {
        return email == null || email.isEmpty();
    }
    private boolean isEmailValid(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern p = Pattern.compile(emailRegex);
        return email != null && p.matcher(email).matches();
    }
}
