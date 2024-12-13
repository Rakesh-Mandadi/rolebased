package com.sb.rolebased.newforgotpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.rolebased.usermanagment.entity.UserRole;
import com.sb.rolebased.usermanagment.repository.UserRepositoryRole;


@Service
public class UserService {

    @Autowired
    private UserRepositoryRole userRepository;

    public void updatePassword(String email, String newPassword) {
        UserRole user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

//        // Hash the password before saving it (use BCrypt or similar)
//        String hashedPassword = passwordEncoder.encode(newPassword);
//        user.setPassword(hashedPassword);

        userRepository.save(user);
    }
}

