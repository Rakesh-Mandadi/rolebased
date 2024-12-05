package com.sb.rolebased.facility.dtos;

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
public class FloorDto {

	private int floorNumber;
	private int totalFlat;
	private int startFlatN;
	private int lastFlatN;
	private BuildingDto buildingDto;
}
