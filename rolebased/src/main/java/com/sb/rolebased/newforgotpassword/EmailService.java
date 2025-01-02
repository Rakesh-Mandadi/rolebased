package com.sb.rolebased.newforgotpassword;

import com.sb.rolebased.forgotpassword.ResetPasswordRequestDto;

public interface EmailService  {
	String sendSimpleMail(EmailDetails details);
	
	boolean verifyOTP(String verifyOTP,String email);
	boolean resetPassword(ResetPasswordRequestDto resetPasswordRequestDto);

}
