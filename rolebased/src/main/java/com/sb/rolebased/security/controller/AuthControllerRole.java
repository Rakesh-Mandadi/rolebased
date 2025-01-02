package com.sb.rolebased.security.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.rolebased.security.constant.SecurityConstantRole;
import com.sb.rolebased.security.dto.JwtResponseRole;
import com.sb.rolebased.security.dto.LoginRequestRole;
import com.sb.rolebased.security.dto.MessageResponseRole;
import com.sb.rolebased.security.dto.SignupRequestRole;
import com.sb.rolebased.security.interfacess.UserDetailsImplRole;
import com.sb.rolebased.security.utils.JwtTokenService;
import com.sb.rolebased.security.utils.JwtUtilsRole;
import com.sb.rolebased.usermanagment.defination.RoleTypeRole;
import com.sb.rolebased.usermanagment.entity.RoleRole;
import com.sb.rolebased.usermanagment.entity.UserRole;
import com.sb.rolebased.usermanagment.repository.RoleRepositoryRole;
import com.sb.rolebased.usermanagment.repository.UserRepositoryRole;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/v1/auth")
public class AuthControllerRole {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserRepositoryRole userRepositoryRole;

	@Autowired
	RoleRepositoryRole roleRepositoryRole;
	
	@Autowired
	JwtUtilsRole jwtUtilsRole;
	
	
	
	
	@PostMapping("/signin")      //"username":"ravis4980", "email":"rssmartbuild@gmail.com",   "password":"ravis123",  "role" : ["ROLE_SUPERADMIN"],
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestRole loginRequestRole ){
		System.out.println("inside AuthControllerRole authenticateUser");
		System.out.println("userName " + loginRequestRole.getUsername());
		System.out.println("password " + loginRequestRole.getPassword()); 
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestRole.getUsername(), loginRequestRole.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		System.out.println("AuthControllerRole 0.1");
		
		String jwt= jwtUtilsRole.generateJwtToken(authentication);
		
		System.out.println("AuthControllerRole 0.2");
		
		UserDetailsImplRole userDetails=(UserDetailsImplRole)  authentication.getPrincipal();
		
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
									.collect(Collectors.toList());
		
		 System.out.println("jwt token ..-> "+ jwt);
		return ResponseEntity.ok(new JwtResponseRole(jwt, SecurityConstantRole.TOKEN_TYPE_DEFAULT ,  userDetails.getId(),
				userDetails.getUsername(),userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")     
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestRole signupRequestRole) {
		System.out.println("AuthControllerRole 1");

		if (userRepositoryRole.existsByName(signupRequestRole.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponseRole("Error : username is alredy taken"));
		}
		if (userRepositoryRole.existsByEmail(signupRequestRole.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponseRole("Error : Email is alredy taken"));
		}

		System.out.println("username "+signupRequestRole.getUsername());
		System.out.println("emil "+signupRequestRole.getEmail());
		System.out.println("contact "+signupRequestRole.getContact());
		System.out.println("pass "+signupRequestRole.getPassword());
		System.out.println("Ident "+signupRequestRole.getIdentify());
		System.out.println("role "+signupRequestRole.getRole().toString());
		
		if(signupRequestRole.getIdentify().equals("BQ31")) {  // BQ31 is code used while generating Super admin 
			
			System.out.println("AuthControllerRole 2");
			UserRole user = new UserRole(signupRequestRole.getUsername(), signupRequestRole.getEmail(),signupRequestRole.getContact(),
					encoder.encode(signupRequestRole.getPassword()));

			Set<String> strRole = signupRequestRole.getRole();

			Set<RoleRole> roles = new HashSet<>();

				strRole.forEach(role -> {
					switch (role) {
					case "ROLE_SUPERADMIN":
						RoleRole supAdminRole = roleRepositoryRole.findByName(RoleTypeRole.ROLE_SUPERADMIN)
								.orElseThrow(() -> new RuntimeException("Erro : RoleNot Found"));
						roles.add(supAdminRole);
						System.out.println("AuthControllerRole 3");
						break;				
					}
				});
			
			user.setRoles(roles);
			userRepositoryRole.save(user);

			return ResponseEntity.ok(new MessageResponseRole("user SuperAdmin created sucsessfully"));
		}else {
			System.out.println("AuthControllerRole 4");
			return ResponseEntity.badRequest().body(new MessageResponseRole("Error : Not Eligible for SUPERADMIN"));
		}
		
	}
	
	

}
