package com.example.shopproject.service;

import com.example.shopproject.entity.User;
import com.example.shopproject.repository.UserRepository;
import com.example.shopproject.request.CreateUserRequest;
import com.example.shopproject.validation.EmailValidation;
import com.example.shopproject.validation.PasswordValidation;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordValidation passwordValidation;
    private final EmailValidation emailValidation;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordValidation passwordValidation, EmailValidation emailValidation, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordValidation = passwordValidation;
        this.emailValidation = emailValidation;
        this.authenticationManager = authenticationManager;
    }

    public String login(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        if(auth.isAuthenticated()) {
            return auth.getName();
        }
        return "null";
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

    public boolean deleteUser(Long userId){
        if(userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        } else {
            return false;
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
