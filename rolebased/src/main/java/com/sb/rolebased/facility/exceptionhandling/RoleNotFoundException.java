package com.sb.rolebased.facility.exceptionhandling;

public class RoleNotFoundException extends RuntimeException{

	public RoleNotFoundException(String message) {
        super(message);
    }
}
