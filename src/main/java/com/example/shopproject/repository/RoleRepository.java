package com.example.shopproject.repository;

import com.example.shopproject.entity.ERole;
import com.example.shopproject.entity.Role;
import com.example.shopproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(ERole role);
    boolean existsByName(ERole role);
}
