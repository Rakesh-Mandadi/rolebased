//package com.sb.rolebased.forgotpassword;
//
//import java.io.IOException;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.sb.rolebased.forgotpassword.ForgotPasswordRequestDto;
//import com.sb.rolebased.forgotpassword.ResetPasswordRequestDto;
//
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//
//@CrossOrigin(origins = "*")
//@RestController
//@RequestMapping("/api/v1/auth")
//public class ForgotPasswordController {
//	
//	@Autowired
//    private ForgotPasswordService forgotPasswordService;
//	
//	
//	
//    @PostMapping("/send-otp")
//    public ResponseEntity<String> sendOtp(@RequestBody AbhiEmail otpRequest) {
//        // Validate the request
//        if ((otpRequest.getEmail() == null || otpRequest.getEmail().isEmpty())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email or Phone is required");
//        }
//
//        // Implement OTP generation logic
//        forgotPasswordService.sendOtp(otpRequest.getEmail());
//        // For demonstration: log or print the OTP
//        //System.out.println("Generated OTP: " + genera);
//
//        return ResponseEntity.ok("OTP sent successfully");
//    }
//	
//	
//	
//
//    // Endpoint to send OTP
////	@PostMapping("/send-otp")
////	public ResponseEntity<?> sendOtp(@RequestBody ForgotPasswordRequestDto request) {
////	    System.out.println("Received Email: " + request.getEmail());
////	    if (request.getEmail() == null || request.getEmail().isEmpty()) {
////	        return ResponseEntity.badRequest().body("Email cannot be null or empty");
////	    }
////	    forgotPasswordService.sendOtp(request.getEmail());
////	    return ResponseEntity.ok("OTP sent successfully");
////	}
//    // Endpoint to reset password
//    @PostMapping("/reset-password")
//    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequestDto request) {
//        forgotPasswordService.resetPassword(request);
//        return ResponseEntity.ok("Password reset successfully");
//    }
////    @RestController
////    @RequestMapping("/api/v1/auth")
////    public class DebugController {
////
////        @PostMapping("/debug-email")
////        public ResponseEntity<?> debugEmail(@RequestBody ForgotPasswordRequestDto request, HttpServletRequest httpRequest) {
////            try {
////                // Log the raw JSON body
////                String rawJson = httpRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
////                System.out.println("Raw JSON received: " + rawJson);
////
////                // Log the deserialized object
////                if (request != null) {
////                    System.out.println("Deserialized DTO: " + request);
////                    System.out.println("Email Field: " + request.getEmail());
////                } else {
////                    System.out.println("Request body is null");
////                }
////
////                // Return response for debugging
////                return ResponseEntity.ok("Check logs for debug information");
////
////            } catch (IOException e) {
////                e.printStackTrace();
////                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading request");
////            }
////        }
////   }
//
//}


