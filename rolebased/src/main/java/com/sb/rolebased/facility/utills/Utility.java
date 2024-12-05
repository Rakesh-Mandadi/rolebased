package com.sb.rolebased.facility.utills;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import com.sb.rolebased.facility.Entity.Building;
import com.sb.rolebased.facility.Entity.Facility;
import com.sb.rolebased.facility.Entity.Flat;
import com.sb.rolebased.facility.Entity.Floor;
import com.sb.rolebased.facility.dtos.BuildingDto;
import com.sb.rolebased.facility.dtos.FacilityDto;
import com.sb.rolebased.facility.dtos.FlatListDto;
import com.sb.rolebased.facility.dtos.FloorDto;
import com.sb.rolebased.facility.dtos.FloorDtoForBldTbl;
import com.sb.rolebased.facility.dtos.FloorListDto;
import com.sb.rolebased.facility.exceptionhandling.ResourceNotFoundException;
import com.sb.rolebased.facility.repository.FacilityRepository;
import com.sb.rolebased.security.dto.FacilityDTO;

public class Utility {
	
	
	public static Facility convertFacilityDtoToEntity(FacilityDto facilityDto) {
	    System.out.println("convertFacilityDtoToEntity 1");
	    System.out.println("convertFacilityDtoToEntity 1"+ facilityDto.getFacilityName());
	    System.out.println("convertFacilityDtoToEntity 1"+ facilityDto.getPin());
	    System.out.println("convertFacilityDtoToEntity 1"+ facilityDto.getMaxBuilding() + " "+ facilityDto.getMaxFloorPerBuilding());

	    Facility facility = Facility.builder()
	        .facilityId(facilityDto.getFacilityId() )
	        .facilityName(facilityDto.getFacilityName())
	        .street(facilityDto.getStreet())
	        .city(facilityDto.getCity())
	        .state(facilityDto.getState())
	        .pin(facilityDto.getPin())
	        .country(facilityDto.getCountry())
	        .maxBuilding(facilityDto.getMaxBuilding())
	        .maxFloorPerBuilding(facilityDto.getMaxFloorPerBuilding())
	        .maxFlatPerFloor(facilityDto.getMaxFlatPerFloor())
	        .build();

	    // Only set buildings if provided
	    if (facilityDto.getBuildingDto() != null) {
	    	System.out.println("facility Utility.. ");
//	        List<Building> buildings = facilityDto.getBuildingDto().stream()
//	            .map(buildingDto -> Building.builder()
//	                .buildingId(buildingDto.getBuildingId())
//	                .buildingName(buildingDto.getBuildingName())
//	                .facility(facility)
//	                .build()
//	            ).collect(Collectors.toList());
//	        facility.setBuilding(buildings);
	    }

	    System.out.println("convertFacilityDtoToEntity 2");
	    return facility;
	}

	
	
/*	public static Facility convertFacilityDtoToEntity(FacilityDto facilityDto) {
		
		System.out.println("convertFacilityDtoToEntity 1");
		
		 Facility facility = Facility.builder().facilityId(facilityDto.getFacilityId())
				.facilityName(facilityDto.getFacilityName())
				.street(facilityDto.getStreet())
				.city(facilityDto.getCity())
				.state(facilityDto.getState())
				.pin(facilityDto.getPin())
				.country(facilityDto.getCountry())
				.maxBuilding(facilityDto.getMaxBuilding())
				.maxFloorPerBuilding(facilityDto.getMaxFloorPerBuilding())
				.maxFlatPerFloor(facilityDto.getMaxFlatPerFloor())
				.build();
		 System.out.println("convertFacilityDtoToEntity 2");
			return facility;
	}
*/
	   public static Building convertBuildingDtoToEntity(BuildingDto buildingDto, Facility existingFacility) {
	        System.out.println("convertBuildingDtoToEntity 3");

	        Building building = new Building();
	        building.setFacility(existingFacility);
	        building.setBuildingName(buildingDto.getBuildingName());;
	        // Set other properties from buildingDto to building

	        List<Floor> floors = buildingDto.getFloorDto().stream()
	                .map(floorDto -> convertFloorDtoToEntity(floorDto, building))
	                .collect(Collectors.toList());
	        building.setFloor(floors);
	        return building;
	    }
	
	/*
	public static List<Building> convertBuildingDtoListToEntity(Facility existingFacility, List<BuildingDto> buildingDtoList) {
	    
		 System.out.println("convertFacilityDtoToEntity 3");
	    return buildingDtoList.stream()
	            .map(buildingDto -> {
	                Building building = convertBuildingDtoToEntity(buildingDto, existingFacility);
	                List<Floor> floors = buildingDto.getFloorDto().stream()
	                        .map(floorDto -> convertFloorDtoToEntity(floorDto, building))
	                        .collect(Collectors.toList());
	                building.setFloor(floors);
	                return building;
	            })
	            .collect(Collectors.toList());
	}
	*/
	
/*	public static Building convertBuildingDtoToEntity(BuildingDto buildingDto, Facility facility) {
	   
		 System.out.println("convertFacilityDtoToEntity 4");
		return Building.builder()
	            .buildingName(buildingDto.getBuildingName())
	            .facility(facility)
	            .build();
	}
*/
        
	public static Floor convertFloorDtoToEntity(FloorDto floorDto, Building building) {
	    System.out.println("convertFloorDtoToEntity 5");
	    Floor floor = Floor.builder()
	            .floorNumber(floorDto.getFloorNumber())
	            .totalFlat(floorDto.getTotalFlat())
	            .startFlatN(floorDto.getStartFlatN())
	            .lastFlatN(floorDto.getLastFlatN())
	            .building(building)
	            .build();

	    // Generate flats for the floor
	    List<Flat> flats = new ArrayList<>();
	    for (int flatNumber = floorDto.getStartFlatN(); flatNumber <= floorDto.getLastFlatN(); flatNumber++) {
	        Flat flat = Flat.builder()
	                .flatNumber((long) flatNumber)
	                .floor(floor)
	                .build();
	        flats.add(flat);
	    }
	    floor.setFlats(flats);
	    return floor;
	}

	public static FacilityDTO convertFacilityToFacilityDTO(Facility saveFacilityDetails) {
		
	return	FacilityDTO.builder()
			.facilityId(saveFacilityDetails.getFacilityId())
			.facilityName(saveFacilityDetails.getFacilityName())
			.street(saveFacilityDetails.getStreet())
			.state(saveFacilityDetails.getState())
			.pin(saveFacilityDetails.getPin())
			.country(saveFacilityDetails.getCountry())
			.build();
	}
	
	
	public static FloorDtoForBldTbl convertFloorToFloorDtoForBldTbl(Floor floor) {
		
		return FloorDtoForBldTbl.builder()
				.floorId(floor.getFloorId())
				.floorNumber(floor.getFloorNumber())
				.flatListDto(floor.getFlats().stream().map(Utility::convertFlatToFlatListDto).collect(Collectors.toList()))
				.build();
	}
	
	public static FlatListDto convertFlatToFlatListDto(Flat flat) {
		return FlatListDto.builder()
				.flatId(flat.getFlatId())
				.flatNumber(flat.getFlatNumber())
				.build();
	}
	
}





/* public static Facility convertFacilityDtoToEntity(FacilityDto facilityDto) {
	
	System.out.println("convertFacilityDtoToEntity 1");
	
	 Facility facility = Facility.builder().facilityId(facilityDto.getFacilityId())
			.facilityName(facilityDto.getFacilityName())
			.street(facilityDto.getStreet())
			.city(facilityDto.getCity())
			.state(facilityDto.getState())
			.pin(facilityDto.getPin())
			.country(facilityDto.getCountry())
			.build();
			//.building(facilityDto.getBuildingDto())  // buildingDto And BuildingEntity obj injection
			
	 System.out.println("convertFacilityDtoToEntity 2");
	 
	 List<BuildingDto> buildingDtoList = facilityDto.getBuildingDto();
	 
	 List<Building> buildings = buildingDtoList.stream()
	  .map(buildingDto ->{
			Building building = convertBuildingDtoToEntity(buildingDto,facility);
			 List<Floor> floors= buildingDto.getFloorDto().stream()
					 .map(floorDto -> convertFloorDtoToEntity(floorDto,building))
					 .collect(Collectors.toList());
			 building.setFloor(floors);
			 return building;
		}).collect(Collectors.toList());
	 
	  
	 System.out.println("convertFacilityDtoToEntity 3");
		facility.setBuilding(buildings);
		
		return facility;
}

    public static Building convertBuildingDtoToEntity(BuildingDto buildingDto, Facility facility) {
	
	System.out.println("convertBuildingDtoToEntity  4");
	return Building.builder()
			.buildingName(buildingDto.getBuildingName())
			.facility(facility)
			.floor(new ArrayList<>())
			.build();
}
    
    public static Floor convertFloorDtoToEntity(FloorDto floorDto,Building building) {
		System.out.println("convertFloorDtoToEntity 5");
		return Floor.builder()
				.floorNumber(floorDto.getFloorNumber())
				.totalFlat(floorDto.getTotalFlat())
				.building(building)
				.build();
	} */
