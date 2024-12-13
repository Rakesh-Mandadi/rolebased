package com.sb.rolebased.newforgotpassword;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
	public class OtpEntity {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String recipient;

	    @Column(nullable = false)
	    private String otp;

	    @Column(nullable = false)
	    private Long generatedAt;
	    @Column(nullable = false)
	    private LocalDateTime expiryTime;

	    // Constructors
	    public OtpEntity() {}

	    public OtpEntity(String recipient, String otp, Long generatedAt) {
	        this.recipient = recipient;
	        this.otp = otp;
	        this.generatedAt = generatedAt;
	    }

//	    // Getters and Setters
//	    public Long getId() {
//	        return id;
//	    }
//
//	    public void setId(Long id) {
//	        this.id = id;
//	    }
//
//	    public String getRecipient() {
//	        return recipient;
//	    }
//
//	    public void setRecipient(String recipient) {
//	        this.recipient = recipient;
//	    }
//
//	    public String getOtp() {
//	        return otp;
//	    }
//
//	    public void setOtp(String otp) {
//	        this.otp = otp;
//	    }
//
//	    public Long getGeneratedAt() {
//	        return generatedAt;
//	    }
//
//	    public void setGeneratedAt(Long generatedAt) {
//	        this.generatedAt = generatedAt;
//	    }
	}


