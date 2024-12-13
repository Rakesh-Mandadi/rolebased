package com.sb.rolebased.newforgotpassword;

import java.time.LocalDateTime;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sb.rolebased.newforgotpassword.*;
import com.sb.rolebased.usermanagment.repository.UserRepositoryRole;
import com.sb.rolebased.newforgotpassword.OtpEntity;
import lombok.Getter;


@Component
public class OtpGenerator {
	
	@Autowired
	private UserRepositoryRole userRepository;


    @Autowired
    @Getter
    private OtpRepository otpRepository;

    public String generateOTP(String recipient) {
    	if (!userRepository.existsByEmail(recipient)) {
            throw new RuntimeException("Recipient email not found in the system.");
        }
        Random random = new Random();
        String otp = String.format("%06d", random.nextInt(1000000)); 

        otpRepository.findByRecipient(recipient).ifPresent(otpEntity -> otpRepository.delete(otpEntity));

        OtpEntity otpEntity = new OtpEntity(recipient, otp, System.currentTimeMillis());
        otpRepository.save(otpEntity);

        return otp;
    }
    public String getGenratedOTP(String recipient){

       OtpEntity acutalotp = otpRepository.findByRecipient(recipient).orElseThrow(()->new RuntimeException("no OTP found "));
        if (acutalotp != null) {
            otpRepository.deleteById(acutalotp.getId());
            return acutalotp.getOtp() ;
            //otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(10));
        } 
        else {
            
            return "";
        }
       
    }

}
