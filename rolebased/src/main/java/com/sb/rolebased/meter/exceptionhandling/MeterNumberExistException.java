package com.sb.rolebased.meter.exceptionhandling;

public class MeterNumberExistException extends RuntimeException {
	
		public MeterNumberExistException(String massage) {
			super(massage);
		}
}
