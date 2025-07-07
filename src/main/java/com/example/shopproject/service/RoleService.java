package com.example.shopproject.service;

import com.example.shopproject.entity.ERole;
import com.example.shopproject.entity.Role;
import com.example.shopproject.exception.CouldNotCreateRole;
import com.example.shopproject.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Role tryCreateRoleUser() throws CouldNotCreateRole {
        if(!roleRepository.existsByName(ERole.ROLE_USER)){
            Role role = new Role(ERole.ROLE_USER);
            roleRepository.save(role);
            return role;
        }
        throw new CouldNotCreateRole("Can not create role");
    }
}
