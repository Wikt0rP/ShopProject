package com.example.shopproject.security;

import com.example.shopproject.entity.User;
import com.example.shopproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class UserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void successfulLoadUserDetails(){
        when(userRepository.findByEmail(any(String.class)))
                .thenReturn(Optional.of(new User("Name", "Surname", "example@mail.com", "password")));

        assertNotNull(userDetailsService.loadUserByUsername("example@mail.com"));
    }

    @Test
    void userDoesNotExists(){
        when(userRepository.findByEmail(any(String.class)))
                .thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("example@mail.com");
        });
    }
}
