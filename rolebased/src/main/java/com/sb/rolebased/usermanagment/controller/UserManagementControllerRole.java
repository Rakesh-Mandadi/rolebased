package com.sb.rolebased.usermanagment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sb.rolebased.usermanagment.entity.UserRole;
import com.sb.rolebased.usermanagment.service.UserManagmentServiceRole;

@RestController
@RequestMapping("api/v1/users")
public class UserManagementControllerRole {

	@Autowired
	UserManagmentServiceRole userManagmentServiceRole;
	
	@GetMapping("/hello")
	public String getHello() {
		System.out.println("hello method");
		return "Hello";
	}
	
	@GetMapping("/allusers")
	@PreAuthorize("hasRole(ADMIN)")
	public ResponseEntity<List<UserRole>> getUser(){
		
		List<UserRole> allUser = userManagmentServiceRole.getAllUser();
		
		return ResponseEntity.status(HttpStatus.OK).body(allUser);
	}
}
