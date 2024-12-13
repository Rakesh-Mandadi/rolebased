package com.sb.rolebased.forgotpassword;

import com.fasterxml.jackson.annotation.JsonProperty;
//import com.sb.rolebased.forgotpassword.ForgotPasswordRequestDto;
import com.sb.rolebased.forgotpassword.ResetPasswordRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder

public class ResetPasswordRequestDto {
	public String email;
	public String otp;
	public String newPassword;

}
