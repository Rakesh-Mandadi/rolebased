package com.sb.rolebased.facility.dtos;

import java.util.List;

import com.sb.rolebased.facility.Entity.Flat;
import com.sb.rolebased.facility.Entity.Floor;

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
public class BuildingMeterDto {
	 private String buildingName;
	 private int floorno;
	    private long flatno;
//	    private long MeterNumber;
	    private String username;
}
