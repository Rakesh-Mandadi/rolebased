package com.sb.rolebased.meter.exceptionhandling;

import java.util.*;

import lombok.Data;

@Data
public class ErrorResponse {

	 private int status;
	    private String message;
	    private String detail; // Add this field
	    private List<ValidationError> errors;
	    
	    
	    public ErrorResponse(int status, String message) {
	        this.status = status;
	        this.message = message;
	        this.errors = new ArrayList<>();
	    }

	    // Add the setter for detail
	    public void setDetail(String detail) {
	        this.detail = detail;
	    }
	    
	    
	    public void addValidationError(String field, String error) {
	        this.errors.add(new ValidationError(field, error));
	    }

	    public static class ValidationError {
	        private String field;
	        private String error;

	        public ValidationError(String field, String error) {
	            this.field = field;
	            this.error = error;
	        }

	        // Getters and setters
	    }
}
