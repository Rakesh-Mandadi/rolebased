package com.sb.rolebased.facility.service;

import java.util.List;

import com.sb.rolebased.facility.Entity.Building;
import com.sb.rolebased.facility.Entity.Facility;
import com.sb.rolebased.facility.dtos.BuildingDto;
import com.sb.rolebased.facility.dtos.BuildingDtoForBldTbl;
import com.sb.rolebased.facility.dtos.BuildingListDto;
import com.sb.rolebased.facility.dtos.BuildingListWithFlatAndMeter;
import com.sb.rolebased.facility.dtos.FacilityDto;
import com.sb.rolebased.facility.dtos.FlatListDto;
import com.sb.rolebased.facility.dtos.FloorListDto;
import com.sb.rolebased.facility.dtos.SubAdNumData;
import com.sb.rolebased.facility.mslnus.PagedResponse;
import com.sb.rolebased.security.dto.FacilityDTO;

public interface FacilityService {

	Facility saveFacilityDetails(FacilityDto facilityDto);

	// List<Building> createBuildings(Long facilityId, List<BuildingDto> buildingDtoList);

	FacilityDTO getFacilityForSubAdmin(Long userIduserId);

	List<BuildingListDto> getBuildingNameList(String facilityId);

	List<FloorListDto> getFloorNumberList(Long buildingId);

	List<FlatListDto> getFlatNumberList(Long floorId);

	Building createBuildings(String facilityId, BuildingDto buildingDto);

	SubAdNumData getSubAdNumData(String facilityId);

	List<BuildingListWithFlatAndMeter> getListOfBuildingFlat(String facilityId);

	Building updateBuilding(String facilityId, BuildingListDto buildingListDto);

	void deleteBuilding(String facilityId, Long buildingId);

	BuildingDtoForBldTbl getBuildingData(String facilityId, Long buildingId);

}
