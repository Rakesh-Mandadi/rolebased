package com.sb.rolebased.security.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter      // replace with @Data if not work
public class JwtResponseRole {
	
	private String token;
	private String type="Bearer";
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
	
}
