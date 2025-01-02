package com.sb.rolebased.security.dto;

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
public class SuperAdNumData {
	
	private Long facilityCount;
	private Long subAdminCount;
	private Long flatCount;
	//private Long meter;
	private Long assignedMeter;
//	private Long unAssignedMeter;
	
	 public SuperAdNumData(long facilityCount, long subAdminCount, long flatCount, long assignedMeter ) {
	        this.facilityCount = facilityCount;
	        this.subAdminCount = subAdminCount;
	        this.flatCount = flatCount;
	        this.assignedMeter = assignedMeter;
//	        this.unAssignedMeter = unAssignedMeter;
 }
}
