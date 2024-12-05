package com.sb.rolebased.usermanagment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.rolebased.usermanagment.entity.UserRole;
import com.sb.rolebased.usermanagment.repository.UserRepositoryRole;

@Service
public class UserManagmentServiceRole {

	@Autowired
	UserRepositoryRole userRepositoryRole;
	
	public Optional<UserRole> getUser(String name){
			System.out.println("UserManagmentServiceRole 1");			
			Optional<UserRole> user = userRepositoryRole.getByName(name);			
			System.out.println("after getting the "+user.get().getPassword());			
		return user;
	}
	
	public List<UserRole> getAllUser(){
		System.out.println("UserManagmentServiceRole 2");
		List<UserRole> allUser = userRepositoryRole.findAll();
		return allUser;
	}
	
}
