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
public class BuildingListWithFlatAndMeter {

	private Long buildingId;
	private String buildingName;
	private Long totalFlats;
	private Long totalMeters;
}
