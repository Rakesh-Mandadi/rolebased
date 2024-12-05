package com.sb.rolebased.security.exceptionhandling;

public class InvalidatedTokenException extends RuntimeException{

	public InvalidatedTokenException(String message) {
		super(message);
	}
}
