package com.sb.rolebased.newforgotpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.rolebased.forgotpassword.ResetPasswordRequestDto;
import com.sb.rolebased.newforgotpassword.*;

@RestController
@RequestMapping("/api/v1/auth")
public class EmailController {
	
	 @Autowired private EmailService emailService;

	    // Sending a simple Email
	    @PostMapping("/sendOTP")
	    public String
	    sendMail(@RequestBody EmailDetails details)
	    {
	        String status
	            = emailService.sendSimpleMail(details);

	        return status;
	    }
	    @PostMapping("/verifyOTP")
		public boolean verifyOTP(@RequestParam String verifyOTP,@RequestParam String email) {
			boolean status = emailService.verifyOTP(verifyOTP,email);
			
			return status;
		}
	    @PostMapping("/resetPassword")
	    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
	        boolean isReset = emailService.resetPassword(resetPasswordRequestDto);

	        if (isReset) {
	            return ResponseEntity.ok("Password reset successfully.");
	        } else {
	            return ResponseEntity.badRequest().body("Invalid OTP or Email.");
	        }
	    }
}
