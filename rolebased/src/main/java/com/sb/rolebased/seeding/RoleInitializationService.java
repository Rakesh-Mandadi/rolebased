package com.sb.rolebased.seeding;
import org.springframework.beans.factory.annotation.Autowired;
import com.sb.rolebased.usermanagment.defination.RoleTypeRole;
import com.sb.rolebased.usermanagment.entity.RoleRole;
import com.sb.rolebased.usermanagment.repository.RoleRepositoryRole;

import jakarta.annotation.PostConstruct;


public class RoleInitializationService {
	 @Autowired
	    private RoleRepositoryRole roleRepository;

	    @PostConstruct
	    public void initializeRoles() {
	        System.out.println("Initializing roles in the database...");

	        try {
	            for (RoleTypeRole roleType : RoleTypeRole.values()) {
	                // Check if the role already exists in the database
	                if (roleRepository.findByName(roleType) == null) {
	                    // Save the new role to the database
	                    roleRepository.save(new RoleRole());
	                    System.out.println("Role added to the database: " + roleType.name());
	                }
	            }
	        } catch (Exception e) {
	            System.err.println("Error initializing roles: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	}
	


