package com.sb.rolebased.meter.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sb.rolebased.billing.exceptionhandling.BuildingExistException;
import com.sb.rolebased.facility.exceptionhandling.FacilityNotFoundException;
import com.sb.rolebased.security.exceptionhandling.InvalidatedTokenException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BuildingExistException.class)
	public ResponseEntity<ErrorResponse> handleBuildingExistException(BuildingExistException ex){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), "Building name already exist");	
		errorResponse.setDetail(ex.getMessage());	
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	}
	
	@ExceptionHandler(InvalidatedTokenException.class)
	public ResponseEntity<ErrorResponse> handleInvalidatedTokenException(InvalidatedTokenException ex){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "token is invalid");
		errorResponse.setDetail(ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
	}
	
	@ExceptionHandler(FacilityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleFacilityNotFoundException(FacilityNotFoundException ex){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Facility not found");
		errorResponse.setDetail(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
        errorResponse.setDetail(ex.getMessage()); // Set the detail message here
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
		
	@ExceptionHandler(MeterNumberExistException.class)
	public ResponseEntity<ErrorResponse> handleMeterNumberExistException(MeterNumberExistException ex){
		
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), "Data Already exist");
		errorResponse.setDetail(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	}
	
	
	
	
}
