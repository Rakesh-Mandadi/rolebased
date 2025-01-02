package com.sb.rolebased.meter.service;

import java.util.List;

import com.sb.rolebased.facility.Entity.Flat;
import com.sb.rolebased.facility.dtos.FlatDto;
import com.sb.rolebased.meter.dto.MeterDto;
import com.sb.rolebased.meter.dto.MeterTpDto;
import com.sb.rolebased.meter.dto.MeterTypeDto;
import com.sb.rolebased.meter.dto.MeterTypeDto.MeterTypeDtoBuilder;
import com.sb.rolebased.meter.dto.MeterTypeStatus;
import com.sb.rolebased.meter.entity.Meter;
import com.sb.rolebased.meter.entity.MeterType;
import com.sb.rolebased.security.dto.FlatMeterLinkResponseDto;

public interface MeterService {

	MeterType saveMeterType(MeterTpDto meterTpDto, String facilityId);

	List<MeterType> getListMeterType(String facilityId, String meterType);
	
	List<MeterTypeDto> getListOfAllMeterType(String facilityId);

	Meter saveMeter(MeterDto meterDto, String facilityId);

	List<Meter> getListOfMeters(String facilityId);

//	List<MeterTypeStatus> checkUnassignedMetersByMeterType(Long facilityId);

	Flat linkFlatAndMeters(FlatDto flatDto, String facilityId);
	
	FlatMeterLinkResponseDto linkFlatsAndMeters(FlatDto flatDto, String facilityId);

	List<MeterTypeStatus> checkUnassignedMetersByMeterType(String facilityId, String meterCat);

	

//	Flat saveFlatDetails(FlatDto flatDto);

}
