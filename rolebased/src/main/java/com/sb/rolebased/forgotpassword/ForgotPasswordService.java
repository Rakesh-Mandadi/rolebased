//package com.sb.rolebased.forgotpassword;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.Random;
//
//import org.apache.catalina.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.sb.rolebased.forgotpassword.ForgotPasswordRequestDto;
//import com.sb.rolebased.forgotpassword.ResetPasswordRequestDto;
//import com.sb.rolebased.usermanagment.entity.UserRole;
//import com.sb.rolebased.usermanagment.repository.UserRepositoryRole;
//import com.sb.rolebased.forgotpassword.EmailService;
//import com.sb.rolebased.forgotpassword.ResetPasswordRequestDto;
//@Service
//public class ForgotPasswordService {
//
//    @Autowired
//    private UserRepositoryRole userRepositoryRole;
//
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private OtpRepository otpRepository;
//
//    // Method to send OTP
//    public void sendOtp(String email) {
//    	System.out.println("email recived in method "+ email);
//    	UserRole byEmail = userRepositoryRole.findByEmail(email)
//    		    .orElseThrow(() -> new RuntimeException("Email ID not found: " + email));
//
//    	System.out.println("email from data base "+ byEmail);
//        String otp = generateOtp();
//        emailService.sendEmail(email, "Password Reset OTP", "Your OTP is: " + otp);
//
//        Otp otpEntity = new Otp();
//        otpEntity.setEmail(email);
//        otpEntity.setOtp(otp);
//        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(10)); // OTP expires in 10 minutes
//        otpRepository.save(otpEntity);
//    }
//
//
//
//    // Method to reset password
//    public void resetPassword(ResetPasswordRequestDto request) {
//        Otp otpEntity = otpRepository.findByEmailAndOtp(request.getEmail(), request.getOtp())
//                .orElseThrow(() -> new RuntimeException("Invalid OTP"));
//
//        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
//            throw new RuntimeException("OTP has expired");
//        }
//
//        User user = (User) userRepositoryRole.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));
//
//        user.setPassword(new BCryptPasswordEncoder().encode(request.getNewPassword()));
//        userRepositoryRole.save(user);
//
//        otpRepository.delete(otpEntity);
//    }
////    public boolean validateOtp(String email, String otp) {
////        Otp storedOtp = otpRepository.findByEmail(email)
////            .orElseThrow(() -> new IllegalArgumentException("OTP not found"));
////
////        if (storedOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
////            throw new IllegalArgumentException("OTP has expired");
////        }
////
////        return storedOtp.getOtp().equals(otp);
////    }
//
//    private String generateOtp() {
//        return String.valueOf((int)(Math.random() * 900000) + 100000);
//    }
//}
