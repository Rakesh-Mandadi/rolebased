package com.sb.rolebased.facility.dtos;

import java.util.List;

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
public class FacilityDto {

	private String facilityId;
	private String facilityName;
	private String street;
	private String city;
	private String state;
	private int pin;
	private String country;
	private int maxBuilding;
	private int maxFloorPerBuilding;
	private int maxFlatPerFloor;
	private List<BuildingDto> buildingDto;
}
