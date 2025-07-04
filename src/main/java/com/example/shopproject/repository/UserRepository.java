package com.example.shopproject.repository;

import com.example.shopproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByName(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findUserById(Long id);

    Boolean existsByName(String name);
    Boolean existsByEmail(String email);

}
