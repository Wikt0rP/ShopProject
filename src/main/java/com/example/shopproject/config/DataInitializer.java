package com.example.shopproject.config;

import com.example.shopproject.entity.ERole;
import com.example.shopproject.exception.CouldNotCreateRole;
import com.example.shopproject.repository.RoleRepository;
import com.example.shopproject.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final RoleService roleService;
    private final RoleRepository roleRepository;

    public DataInitializer(RoleService roleService, RoleRepository roleRepository){
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.existsByName(ERole.ROLE_USER))
            return;

        try{
            roleService.tryCreateRoleUser();
        }catch (CouldNotCreateRole rEx){
            logger.error("Role_User could not be created at server start");
        }
    }
}
