package com.sb.rolebased.security.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.rolebased.security.constant.SecurityConstantRole;
import com.sb.rolebased.security.dto.JwtResponseRole;
import com.sb.rolebased.security.dto.LoginRequestRole;
import com.sb.rolebased.security.interfacess.UserDetailsImplRole;
import com.sb.rolebased.security.utils.JwtUtilsRole;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class SubAdminControllerRole {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtilsRole jwtUtilsRole;
	
	
	@PostMapping("/subAdmin/signin")      //"username" : "saisir","email" : "example@gmail.com","password" : "saisir123","role" : ["ROLE_SUBADMIN"]
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestRole loginRequestRole ){
		System.out.println("inside SubAdminControllerRole authenticateUser");
		System.out.println("userName " + loginRequestRole.getUsername());
		System.out.println("password " + loginRequestRole.getPassword()); 
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestRole.getUsername(), loginRequestRole.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		System.out.println("SubAdminControllerRole 0.1");
		
		String jwt= jwtUtilsRole.generateJwtToken(authentication);
		
		System.out.println("SubAdminControllerRole 0.2");
		
		UserDetailsImplRole userDetails=(UserDetailsImplRole)  authentication.getPrincipal();
		
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
									.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponseRole(jwt, SecurityConstantRole.TOKEN_TYPE_DEFAULT ,  userDetails.getId(),
				userDetails.getUsername(),userDetails.getEmail(), roles));
	}
}
