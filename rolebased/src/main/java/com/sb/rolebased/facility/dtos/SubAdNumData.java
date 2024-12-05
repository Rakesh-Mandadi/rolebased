package com.sb.rolebased.facility.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SubAdNumData {

	private Long buildingCount;
	private Long flatCount;
	private Long meterCount;
}
