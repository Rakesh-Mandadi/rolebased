package com.sb.rolebased.facility.exceptionhandling;

public class ResourceNotFoundException  extends RuntimeException{

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
