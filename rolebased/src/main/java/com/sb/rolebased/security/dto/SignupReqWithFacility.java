package com.sb.rolebased.security.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SignupReqWithFacility {

	 @NotBlank
	    @Size(min = 3, max = 20)
	private String username;
	
	  @NotBlank
	    @Size(max = 50)
	    @Email
	private String email;
	
	  @NotBlank
	    @Size(min = 6, max = 40)
	private String password;
	  
	//private String identify;
	
	private String facilityId;
	
	private String facilityName;
	
	private Set<String> role;
}
