package com.sb.rolebased.meter.dto;

import com.sb.rolebased.facility.Entity.Flat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MeterDto {
	
    private Long meterId;
    private Long meterType;
    private Long meterNumber;
    private String ieeeAdd;
    private boolean status;
   
    
    private Long facilityId; // Add this field
}
