package com.example.shopproject.controller;

import com.example.shopproject.request.CreateUserRequest;
import com.example.shopproject.request.UserLoginRequest;
import com.example.shopproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:63342")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test(){
        return "User controller is working!";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest userLoginRequest,  HttpSession session) {
        return userService.login(userLoginRequest, session);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }

    @GetMapping("/login-status")
    public String checkLoginStatus(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }

        return "not logged";
    }
}
