package com.sb.rolebased.facility.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SubFlatDto {
	   
		private String facilityId;
	    private long flatNumber;
	    private int floorNumber;
	    private String buildingName;
	    private Long flatId;
	    
	    public SubFlatDto(long flatNumber, int floorNumber, String buildingName, Long flatId) {
			super();
			this.flatNumber = flatNumber;
			this.floorNumber = floorNumber;
			this.buildingName = buildingName;
			this.flatId = flatId;
		}

}
