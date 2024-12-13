package com.sb.rolebased.newforgotpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.sb.rolebased.forgotpassword.ResetPasswordRequestDto;
import com.sb.rolebased.newforgotpassword.OtpRepository;
import com.sb.rolebased.usermanagment.repository.UserRepositoryRole;

import jakarta.mail.internet.MimeMessage;

// import jdk.internal.net.http.common.Log;

//import jdk.internal.org.jline.utils.Log;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private OtpGenerator otpGenerator;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepositoryRole userRepository;

    @Value("${spring.mail.username}")
    private String sender;

    

    @Override
    public String sendSimpleMail(EmailDetails details) {
        try {
        	if (!userRepository.existsByEmail(details.getRecipient())) {
                return "Error: Recipient email is not registered.";
        	}
            // Generate OTP and store it
            String otp = otpGenerator.generateOTP(details.getRecipient());

            details.setMsgBody("Your OTP is: " + otp);
            details.setSubject("OTP for Automatic Meter Reading  Application ");

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        	} catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return "Error while Sending Mail: " + e.getMessage();
        }
    }

    @Override
    public boolean verifyOTP(String verifyOTP,String email) {
        String OTP = otpGenerator.getGenratedOTP(email);
        if(verifyOTP.equals(OTP)){
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public boolean resetPassword(ResetPasswordRequestDto resetPasswordRequestDto) {
        String email = resetPasswordRequestDto.getEmail();
        String providedOtp = resetPasswordRequestDto.getOtp();
        String newPassword = resetPasswordRequestDto.getNewPassword();

        // Retrieve the OTP from the database
        OtpEntity otpEntity = otpGenerator.getOtpRepository().findByRecipient(email)
                .orElseThrow(() -> new RuntimeException("No OTP found for this email."));

        // Verify OTP
        if (providedOtp.equals(otpEntity.getOtp())) {
            // Here you would update the user's password in your user repository
            // Assuming you have a UserService with an updatePassword method:
            userService.updatePassword(email, newPassword);

            // Delete the OTP record after successful verification
            otpRepository.deleteByRecipient(email);
            return true;
        } 
        else {
            return false;
        }
    }

}