package com.sb.rolebased.security.interfacess;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.sb.rolebased.forgotpassword.ForgotPasswordDto;
//import com.sb.rolebased.forgotpassword.ForgotPasswordRequestDto;
import com.sb.rolebased.usermanagment.entity.UserRole;
import com.sb.rolebased.usermanagment.repository.UserRepositoryRole;

import jakarta.persistence.OneToOne;


public class UserDetailsImplRole implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String username;
	
	@JsonIgnore
	private String password;
	
	private String email;
	


	private Collection<? extends GrantedAuthority> authorities;
	
  //  @OneToOne(mappedBy = "userdetailsimplrole")
    //private ForgotPasswordRequestDto forgotPasswordRequestDto;
	 
	
	public UserDetailsImplRole(String email, String username, String password, Long long1,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = long1;
		this.username = username;
		this.password = password;
		this.email = email;
		this.authorities = authorities;
		
		System.out.println("UserDetailsImplRole 1");
	}
	
	public static UserDetailsImplRole build(UserRole userRole) {
		System.out.println("UserDetailsImplRole 2");
		List<GrantedAuthority> garntauth= userRole.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
			
		
		return new UserDetailsImplRole(userRole.getEmail(), userRole.getName(), userRole.getPassword(), userRole.getId(), garntauth);
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		System.out.println("UserDetailsImplRole 3");
		return authorities;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	@Override
	public boolean equals(Object o) {
		System.out.println("UserDetailsImplRole 4");
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImplRole user = (UserDetailsImplRole) o;
		return Objects.equals(id, user.id);
	}
	 @Autowired
	    private UserRepositoryRole userRepository;

	    public void updatePassword(String email, String newPassword) {
	        UserRole user = userRepository.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("User not found."));

	        // Hash the password before saving it (use BCrypt or similar)
//	        String hashedPassword = passwordEncoder.BCryptPasswordEncoder(newPassword);
//	        user.setPassword(hashedPassword);

	        userRepository.save(user);
	    }

}
