package com.example.shopproject.security;

import com.example.shopproject.entity.ERole;
import com.example.shopproject.entity.Role;
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
public class UserDetailsServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void successfulLoadUserDetails(){
        when(userRepository.findByEmail(any(String.class)))
                .thenReturn(Optional.of(new User("Name", "Surname", "example@mail.com", "password", new Role(ERole.ROLE_USER))));

        assertNotNull(userDetailsServiceImpl.loadUserByUsername("example@mail.com"));
    }

    @Test
    void userDoesNotExists(){
        when(userRepository.findByEmail(any(String.class)))
                .thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsServiceImpl.loadUserByUsername("example@mail.com");
        });
    }
}
