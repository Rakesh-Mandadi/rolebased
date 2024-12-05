package com.sb.rolebased.meter.utills;

import com.sb.rolebased.facility.Entity.Facility;
import com.sb.rolebased.meter.dto.MeterDto;
import com.sb.rolebased.meter.entity.Meter;

public class MeterUtility {

	public static Meter convertMeterDtoToMeterEntity(MeterDto meterDto, Facility facility) {
	    System.out.println("MeterUtility 1");
	    return Meter.builder()
	        .meterType(meterDto.getMeterType())
	        .meterNumber(meterDto.getMeterNumber())
	        .ieeeAdd(meterDto.getIeeeAdd())
	        .macAdd(setMacAdd(meterDto.getIeeeAdd(), meterDto.getMeterNumber()))
	        .facility(facility)
	        .build();
	}

	public static String setMacAdd(String hexValue, Long meterNumber) {
	   
		System.out.println("setMacAdd :"+ hexValue);
		System.out.println("setMacAdd :"+ meterNumber);
	    if (hexValue == null || hexValue.isEmpty()) {
	        hexValue = "00000000"; 
	    }

	    // Ensure hexValue is exactly 8 characters (4 bytes) long
	  //  hexValue = String.format("%08s", hexValue).replace(' ', '0');

	    // Take only the last 8 characters if it's longer
	    if (hexValue.length() > 8) {
	    	throw new IllegalArgumentException("Provided hex value exceeds 8 bytes");
	    }

	    System.out.println("setMacAdd :");
	    // Convert meterNumber to hex, ensuring it's 8 characters long
	    String decimalHex = String.format("%08x", meterNumber);

	    System.out.println("setMacAdd :"+ hexValue + decimalHex);
	    // Combine the two hex strings
	    return hexValue + decimalHex;
	}
}
