package com.sb.rolebased.usermanagment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sb.rolebased.usermanagment.defination.RoleTypeRole;
import com.sb.rolebased.usermanagment.entity.RoleRole;
import com.sb.rolebased.usermanagment.entity.UserRole;

@Repository
public interface UserRepositoryRole extends JpaRepository<UserRole, Long> {

	boolean existsByName(String username);

	boolean existsByEmail(String email);

	Optional<UserRole> getByName(String name);

	
}
