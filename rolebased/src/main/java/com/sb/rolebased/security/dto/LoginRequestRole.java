package com.sb.rolebased.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter                // if not working properly replace with @Data annotation
public class LoginRequestRole {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
}
