package com.example.shopproject.service;

import com.example.shopproject.entity.User;
import com.example.shopproject.exception.UserNotAuthorizedException;
import com.example.shopproject.exception.UserNotFoundException;
import com.example.shopproject.repository.UserRepository;
import com.example.shopproject.security.UserDetailsImpl;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private final UserRepository userRepository;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserFromAuth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken){
            throw new UserNotAuthorizedException("User not authorized");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userRepository.findUserById(userDetails.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found by repo"));
    }
}
