package com.sb.rolebased.usermanagment.repository;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import com.sb.rolebased.forgotpassword.Otp;
import com.sb.rolebased.usermanagment.defination.RoleTypeRole;
import com.sb.rolebased.usermanagment.entity.RoleRole;
import com.sb.rolebased.usermanagment.entity.UserRole;

@Repository
public interface UserRepositoryRole extends JpaRepository<UserRole, Long> {
	 List<UserRole> findByPassword(String password);

	boolean existsByName(String username);

	boolean existsByEmail(String email);
	
	Optional<UserRole> getByName(String name);
	@Query("UPDATE UserRole u SET u.password = :password WHERE u.email = :email")
    void updatePassword(@Param("email") String email, @Param("password") String password);
	<S extends UserRole> S save(User user);
    Optional<UserRole> findByEmail(String email);

	
}
