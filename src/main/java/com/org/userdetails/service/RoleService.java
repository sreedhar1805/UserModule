package com.org.userdetails.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.userdetails.model.Erole;
import com.org.userdetails.model.Role;
import com.org.userdetails.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
    private RoleRepository roleRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    public void createDefaultRoles() {
        
    	 boolean isAdminRolePresent = roleRepository.existsByName(Erole.ROLE_USER);

         if (!isAdminRolePresent) {
        	 Role userRole = new Role();
             userRole.setName(Erole.ROLE_USER);
             logger.info("\u001B[32mDefault Roles configred in the database successfully!\u001B[0m");
             roleRepository.save(userRole);
         }
    }

}
