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
public class BuildingDto {

	private String buildingName;
	private FacilityDto facilityDto;
	private List<FloorDto> floorDto;
}
