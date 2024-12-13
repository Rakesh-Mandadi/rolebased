//package com.sb.rolebased.forgotpassword;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.sb.rolebased.forgotpassword.ForgotPasswordRequestDto;
//import com.sb.rolebased.forgotpassword.ResetPasswordRequestDto;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Builder
//@ToString
//public class ForgotPasswordRequestDto {
//	 
//	    @Email(message = "Invalid email format")
//        @NotNull(message = "Email cannot be null")
//	     @JsonProperty("email")
//	        public String email;
//
//			
//	    }
//
//
