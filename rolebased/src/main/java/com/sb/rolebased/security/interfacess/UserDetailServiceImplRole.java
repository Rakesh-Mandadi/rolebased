package com.sb.rolebased.security.interfacess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sb.rolebased.usermanagment.entity.UserRole;
import com.sb.rolebased.usermanagment.service.UserManagmentServiceRole;

import jakarta.transaction.Transactional;

@Service
public class UserDetailServiceImplRole implements UserDetailsService  {
	
	@Autowired
	UserManagmentServiceRole userManagmentServiceRole;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			System.out.println("UserDetailServiceImplRole 1");
		UserRole user = userManagmentServiceRole.getUser(username)
		.orElseThrow(()-> new UsernameNotFoundException( "user not found with username : "+ username));
		
		return UserDetailsImplRole.build(user);
	}

}
