package com.example.shopproject.service;

import com.example.shopproject.entity.ERole;
import com.example.shopproject.entity.Role;
import com.example.shopproject.entity.User;
import com.example.shopproject.repository.RoleRepository;
import com.example.shopproject.repository.UserRepository;
import com.example.shopproject.request.CreateUserRequest;
import com.example.shopproject.request.UserLoginRequest;
import com.example.shopproject.security.PasswordEncoderConfig;
import com.example.shopproject.validation.EmailValidation;
import com.example.shopproject.validation.PasswordValidation;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final RoleService roleService;

    private final PasswordValidation passwordValidation;
    private final PasswordEncoderConfig passwordEncoder;

    private final EmailValidation emailValidation;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordValidation passwordValidation, EmailValidation emailValidation,
                       AuthenticationManager authenticationManager, RoleRepository roleRepository, RoleService roleService,
                       PasswordEncoderConfig passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordValidation = passwordValidation;
        this.emailValidation = emailValidation;
        this.authenticationManager = authenticationManager;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(UserLoginRequest userLoginRequest, HttpSession session) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword());

        Authentication auth = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        if (auth.isAuthenticated()) {
            return auth.getName();
        }
        return "null";
    }



    public ResponseEntity<?> createUser(CreateUserRequest createUserRequest) {
        //TODO: add checking: password contains username / name and surname
        boolean isValidPassword = passwordValidation
                .isValidPassword(createUserRequest.getPassword(), createUserRequest.getName(), 3);
        boolean isValidEmail = emailValidation.isValidEmail(createUserRequest.getEmail());
        if(!isValidPassword || !isValidEmail) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }
        Role role = roleRepository.findRoleByName(ERole.ROLE_USER)
                .orElseGet(roleService::tryCreateRoleUser);
        User user = new User(createUserRequest.getName(), createUserRequest.getSurname(), createUserRequest.getEmail(),
                passwordEncoder.passwordEncoder().encode(createUserRequest.getPassword()) , role);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user.getName());
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
