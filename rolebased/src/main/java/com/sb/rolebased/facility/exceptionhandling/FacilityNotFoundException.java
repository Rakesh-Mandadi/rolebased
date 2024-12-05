package com.sb.rolebased.facility.exceptionhandling;

public class FacilityNotFoundException extends RuntimeException {

	public FacilityNotFoundException(String message) {
		super(message);
	}
}
