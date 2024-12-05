package com.sb.rolebased.meter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeterTpDto {

	public String meterCat;
	
	public String MeterTypeName;
	
	public double unitRate;
}
