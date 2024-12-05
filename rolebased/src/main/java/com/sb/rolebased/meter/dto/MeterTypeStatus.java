package com.sb.rolebased.meter.dto;

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
public class MeterTypeStatus {

	private String meterTypeName;
	private Long meterTypeId;
    private boolean isAvailable;


}
