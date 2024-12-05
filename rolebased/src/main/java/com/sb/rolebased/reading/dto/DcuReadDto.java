package com.sb.rolebased.reading.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DcuReadDto {

	public String dcuId;
	
	public int lstRead;
	
	public int crtRead;
	
	public Long flatNum;
	
	public Long meterNumber;
}
