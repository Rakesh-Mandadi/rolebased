package com.sb.rolebased.usermanagment.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "name"),
		@UniqueConstraint(columnNames = "email") })
@Data
@NoArgsConstructor
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String name;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotEmpty
	@Size(max = 150)
	@JsonIgnore
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
									inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleRole> roles = new HashSet<>();
	
	

	public UserRole(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		


	}
	
}
