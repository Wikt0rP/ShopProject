package com.example.shopproject.service;

import com.example.shopproject.entity.User;
import com.example.shopproject.repository.UserRepository;
import com.example.shopproject.request.CreateUserRequest;
import com.example.shopproject.validation.EmailValidation;
import com.example.shopproject.validation.PasswordValidation;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordValidation passwordValidation;
    private final EmailValidation emailValidation;

    public UserService(UserRepository userRepository, PasswordValidation passwordValidation, EmailValidation emailValidation) {
        this.userRepository = userRepository;
        this.passwordValidation = passwordValidation;
        this.emailValidation = emailValidation;
    }

    public User createUser(CreateUserRequest createUserRequest) {
        //TODO: add checking: password contains username / name and surname
        boolean isValidPassword = passwordValidation
                .isValidPassword(createUserRequest.getPassword(), createUserRequest.getName(), 3);
        boolean isValidEmail = emailValidation.isValidEmail(createUserRequest.getEmail());
        if(!isValidPassword || !isValidEmail) {
            return null;
        }

        User user = new User(createUserRequest.getName(), createUserRequest.getSurname(),
                createUserRequest.getEmail(), createUserRequest.getPassword());

        userRepository.save(user);
        return user;
    }
}
