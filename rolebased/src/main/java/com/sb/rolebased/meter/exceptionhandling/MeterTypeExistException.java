package com.sb.rolebased.meter.exceptionhandling;

public class MeterTypeExistException extends RuntimeException {

	public MeterTypeExistException(String message) {
        super(message);
    }
}
