package com.example.shopproject.repository;

import com.example.shopproject.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findUserById(Long id);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
