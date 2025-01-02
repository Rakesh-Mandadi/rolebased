package com.sb.rolebased.facility.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.rolebased.billing.exceptionhandling.BuildingExistException;
import com.sb.rolebased.facility.Entity.Building;
import com.sb.rolebased.facility.Entity.Facility;
import com.sb.rolebased.facility.Entity.Flat;
import com.sb.rolebased.facility.Entity.Floor;
import com.sb.rolebased.facility.dtos.BuildingDto;
import com.sb.rolebased.facility.dtos.BuildingDtoForBldTbl;
import com.sb.rolebased.facility.dtos.BuildingListDto;
import com.sb.rolebased.facility.dtos.BuildingListWithFlatAndMeter;
import com.sb.rolebased.facility.dtos.FacilityDto;
import com.sb.rolebased.facility.dtos.FlatListDto;
import com.sb.rolebased.facility.dtos.FloorListDto;
import com.sb.rolebased.facility.dtos.SubAdNumData;
import com.sb.rolebased.facility.exceptionhandling.ResourceNotFoundException;
import com.sb.rolebased.facility.mslnus.PagedResponse;
import com.sb.rolebased.facility.repository.BuildingRepository;
import com.sb.rolebased.facility.repository.FacilityRepository;
import com.sb.rolebased.facility.repository.FlatRepository;
import com.sb.rolebased.facility.repository.FloorRepository;
import com.sb.rolebased.facility.utills.Utility;
import com.sb.rolebased.meter.dto.MeterTypeDto;
import com.sb.rolebased.meter.entity.Meter;
import com.sb.rolebased.meter.repository.MeterRepository;
import com.sb.rolebased.security.dto.FacilityDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class FacilityServiceImp implements FacilityService {
	
	@Autowired
	FacilityRepository facilityRepository;
	
	@Autowired
	BuildingRepository buildingRepository;
	
	@Autowired
	FloorRepository floorRepository;
	
	@Autowired
	FlatRepository flatRepository;
	
	@Autowired
	MeterRepository meterRepository;
	
	@PersistenceContext
    private EntityManager entityManager;

//	@Override
//	public Facility saveFacilityDetails(FacilityDto facilityDto) {
//	
//		Facility facility = Utility.convertFacilityDtoToEntity(facilityDto);
//		
//		Facility save = facilityRepository.save(facility);
//		
//		return save;
//	}
	
	@Override
	public Facility saveFacilityDetails(FacilityDto facilityDto) {
	    Facility existingFacility = facilityRepository.findById(facilityDto.getFacilityId())
	        .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + facilityDto.getFacilityId()));

	    Facility updatedFacility = Utility.convertFacilityDtoToEntity(facilityDto);

	    // Detach the building list if not provided in DTO
	    if (facilityDto.getBuildingDto() == null) {
	        updatedFacility.setBuilding(existingFacility.getBuilding());
	    }
	    Facility savedFacility = facilityRepository.save(updatedFacility);
	    return savedFacility;
	}
	
	
	 @Override
	    public Building createBuildings(String facilityId, BuildingDto buildingDto) {
	        System.out.println("FacilityServiceImp 2");

	        Facility existingFacility = facilityRepository.findById(facilityId)
	                .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + facilityId));

	       
	       // buildingRepository.getByBuildingNameAndFacility(buildingDto.getBuildingName(), facilityId);
	    
	        boolean present = buildingRepository.findByBuildingNameAndFacility_FacilityId(buildingDto.getBuildingName(), facilityId).isPresent();
	        
	        if(present) {
	    	System.out.println("FacilityServiceImp 3");
	        	throw new BuildingExistException("Building name already exist: ");
	        }
	        
	        
	        Building building = Utility.convertBuildingDtoToEntity(buildingDto, existingFacility);

	        Building savedBuilding = buildingRepository.save(building);
	        return savedBuilding;
	    }
	
	// below code is for saving List of Buildings so keep it for future use
	
/*	@Override
	public List<Building> createBuildings(Long facilityId, List<BuildingDto> buildingDtoList) {
		System.out.println("FacilityServiceImp 2");
		
		Facility existingFacility = facilityRepository.findById(facilityId)
	            .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + facilityId));
		
		List<Building> convertBuildingDtoListToEntity = Utility.convertBuildingDtoListToEntity(existingFacility, buildingDtoList);
	        
		List<Building> saveAllBuilding = buildingRepository.saveAll(convertBuildingDtoListToEntity);
		return saveAllBuilding;
	    }
*/
	
	@Override
	public FacilityDTO getFacilityForSubAdmin(Long userId) {
		
		FacilityDTO findFacilityByUserId = facilityRepository.findFacilityByUserId(userId);
		
		return findFacilityByUserId;		
	}

	@Override
	public List<BuildingListDto> getBuildingNameList(String facilityId) {
		
		System.out.println("FacilityServiceImp 3");
		List<BuildingListDto> findByFacility = buildingRepository.findByFacility(facilityId);
		
		return findByFacility;
	}

	@Override
	public List<FloorListDto> getFloorNumberList(Long buildingId) {
		
		List<FloorListDto> findByBuildingId = floorRepository.findByBuildingId(buildingId);
		
		return findByBuildingId;
	}

	@Override
	public List<FlatListDto> getFlatNumberList(Long floorId) {
		
		List<FlatListDto> findByFloorId = flatRepository.findByFloorId(floorId);
		
		return findByFloorId;
	}




	@Override
	 public SubAdNumData getSubAdNumData(String facilityId) {
        // Fetch buildings by facilityId
        List<Building> buildings = buildingRepository.findByFacilityFacilityId(facilityId);
        List<Long> buildingIds = buildings.stream().map(Building::getBuildingId).collect(Collectors.toList());

        // Count buildings
        Long buildingCount = (long) buildings.size();

        // Fetch floors by buildingIds
        List<Floor> floors = floorRepository.findByBuildingBuildingIdIn(buildingIds);
        List<Long> floorIds = floors.stream().map(Floor::getFloorId).collect(Collectors.toList());

        // Fetch flats by floorIds
        List<Flat> flats = flatRepository.findByFloorFloorIdIn(floorIds);
        List<Long> flatIds = flats.stream().map(Flat::getFlatId).collect(Collectors.toList());
        Long flatCount = (long) flats.size();

        // Fetch meters by flatIds
        List<Meter> meters = meterRepository.findByFlatFlatIdIn(flatIds);
        Long meterCount = (long) meters.size();

        return new SubAdNumData(buildingCount, flatCount, meterCount);
    }


	@Override
	public List<BuildingListWithFlatAndMeter> getListOfBuildingFlat(String facilityId) {
	
		// List<Building> buildings = buildingRepository.findByFacilityFacilityId(facilityId);
		
		List<BuildingListWithFlatAndMeter> findBuildingListTotalFlatsTotalMeters = buildingRepository.findBuildingListTotalFlatsTotalMeters(facilityId);
		 return findBuildingListTotalFlatsTotalMeters;
	}

	@Override
	public Building updateBuilding(String facilityId, BuildingListDto buildingListDto) {
	    Facility existingFacility = facilityRepository.findById(facilityId)
	            .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + facilityId));

	    Building existingBuilding = buildingRepository.findById(buildingListDto.getBuildingId())
	            .orElseThrow(() -> new ResourceNotFoundException("Building not found with id: " + buildingListDto.getBuildingId()));

	    // Update the existing building with the new data from buildingDto
	    existingBuilding.setBuildingName(buildingListDto.getBuildingName());
	    // Update other properties from buildingDto to existingBuilding
	    return buildingRepository.save(existingBuilding);
	}

	@Override
	@Transactional
	public void deleteBuilding(String facilityId, Long buildingId) {
	    Facility existingFacility = facilityRepository.findById(facilityId)
	        .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + facilityId));

	    Building buildingToDelete = existingFacility.getBuilding().stream()
	        .filter(building -> building.getBuildingId().equals(buildingId))
	        .findFirst()
	        .orElseThrow(() -> new ResourceNotFoundException("Building not found with id: " + buildingId));

	    // Set Meter's flatId to null
	    buildingToDelete.getFloor().stream()
	        .flatMap(floor -> floor.getFlats().stream())
	        .forEach(flat -> meterRepository.setFlatToNullForFlatId(flat.getFlatId()));

	    // Remove the building from the facility (this will trigger orphan removal)
	    existingFacility.getBuilding().remove(buildingToDelete);

	    // Save the facility
	    facilityRepository.save(existingFacility);

	    System.out.println("Building deleted successfully");
	}


	@Override
	public BuildingDtoForBldTbl getBuildingData(String facilityId, Long buildingId) {
	  	   Optional<Building> findByBuildingId = buildingRepository.findById(buildingId);

	   if(findByBuildingId.isPresent()) {
		   System.out.println("getBuildingData");
		   Building building = findByBuildingId.get();
		 BuildingDtoForBldTbl buildingDtoForBldTbl = new  BuildingDtoForBldTbl();
		   return buildingDtoForBldTbl.builder()
				   .buildingId(building.getBuildingId())
				   .buildingName(building.getBuildingName())
				   .floorDtoForBldTbl(building.getFloor().stream().map(Utility::convertFloorToFloorDtoForBldTbl).collect(Collectors.toList()))
				   .build();
	   }
	    throw new ResourceNotFoundException("Building not found");
	}
	
	
	
	
	
/*	public void deleteBuilding(Long facilityId, Long buildingId) {
	    Facility existingFacility = facilityRepository.findById(facilityId)
	        .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + facilityId));

	    Building buildingToDelete = existingFacility.getBuilding().stream()
	        .filter(building -> building.getBuildingId().equals(buildingId))
	        .findFirst()
	        .orElseThrow(() -> new ResourceNotFoundException("Building not found with id: " + buildingId));

	    // Set Meter's flatId to null
	    buildingToDelete.getFloor().forEach(floor ->
	        floor.getFlats().forEach(flat ->
	            flat.getMeters().forEach(meter -> meter.setFlat(null))
	        )
	    );

	    // Remove the building from the facility
	    existingFacility.getBuilding().remove(buildingToDelete);

	    // Save the facility (this will cascade the delete operation)
	    facilityRepository.save(existingFacility);

	    System.out.println("Building deleted successfully");
	} */
/*	public void deleteBuilding(Long facilityId, Long buildingId) {
	    Facility existingFacility = facilityRepository.findById(facilityId)
	        .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + facilityId));

	    Building buildingToDelete = existingFacility.getBuilding().stream()
	        .filter(building -> building.getBuildingId().equals(buildingId))
	        .findFirst()
	        .orElseThrow(() -> new ResourceNotFoundException("Building not found with id: " + buildingId));

	    // Manually remove all related entities
	    buildingToDelete.getFloor().forEach(floor -> {
	        floor.getFlats().forEach(flat -> {
	            // Set Meter's flatId to null
	            flat.getMeters().forEach(meter -> {
	                meter.setFlat(null);
	                meterRepository.save(meter);
	            });
	            // Clear and remove flats
	            flat.getMeters().clear();
	            flatRepository.delete(flat);
	        });
	        // Clear and remove floors
	        floor.getFlats().clear();
	        floorRepository.delete(floor);
	    });

	    // Clear and remove building
	    buildingToDelete.getFloor().clear();
	    existingFacility.getBuilding().remove(buildingToDelete);

	    // Save the facility and delete the building
	    facilityRepository.save(existingFacility);
	    buildingRepository.delete(buildingToDelete);

	    System.out.println("Building deleted successfully");
	}
*/
	
	
	
/*	 public void deleteBuilding(Long facilityId, Long buildingId) {
        Facility existingFacility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + facilityId));

        Building buildingToDelete = existingFacility.getBuilding().stream()
                .filter(building -> building.getBuildingId().equals(buildingId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Building not found with id: " + buildingId));

        for (Floor floor : buildingToDelete.getFloor()) {
            for (Flat flat : floor.getFlats()) {
                meterRepository.setFlatToNullForFlatId(flat.getFlatId());
            }
            floor.getFlats().clear();  // This should trigger orphan removal
        }

        buildingToDelete.getFloor().clear();  // This should trigger orphan removal

        existingFacility.getBuilding().remove(buildingToDelete);  // This should trigger orphan removal

        facilityRepository.save(existingFacility);  // Save the updated facility

        System.out.println("Building deleted successfully");
    }
	*/
	
	
/*	public void deleteBuilding(Long facilityId, Long buildingId) {
	    System.out.println("FacilityServiceImp 3");
	    Facility existingFacility = facilityRepository.findById(facilityId)
	            .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + facilityId));

	    List<Building> buildings = existingFacility.getBuilding();
	    buildings.stream()
	           .map(Building::getBuildingName)
	           .forEach(System.out::println);
	    System.out.println("FacilityServiceImp 4 -" + existingFacility);

	    // Find the building to be deleted
	    Building buildingToDelete = buildings.stream()
	            .filter(building -> building.getBuildingId().equals(buildingId))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException("Building not found with id: " + buildingId));

	    System.out.println("FacilityServiceImp 5 -" + buildingToDelete.getBuildingName());

	    List<Floor> list2 = buildingToDelete.getFloor();
	    list2.stream().map(Floor::getFloorNumber).forEach(System.out::println);
	    // Set flat reference in Meter to null
	    for (Floor floor : buildingToDelete.getFloor()) {
	    	
	    	System.out.println("FacilityServiceImp 6 -"+floor);
	    	List<Flat> list3 = floor.getFlats();
	    	
	    	list3.stream().map(Flat::getFlatNumber).forEach(System.out::println);
	    	
	    	
	        for (Flat flat : floor.getFlats()) {
	        	System.out.println("FacilityServiceImp 6 -"+flat);
	        	
	        	List<Meter> list4 = flat.getMeters();
	        	list4.stream().map(Meter::getMeterNumber).forEach(System.out::println);
	        	
	        	System.out.println("meterRepository quiry -"+ flat.getFlatId());
	            meterRepository.setFlatToNullForFlatId(flat.getFlatId());
	        }
	    }

	    // Remove the floors associated with the building
	    List<Floor> floors = buildingToDelete.getFloor();
	    for (Floor floor : floors) {
	        floor.getFlats().clear(); // Clear the flats associated with each floor
	        
	        System.out.println("afetr removing flats on floor 1 ");
	        List<Flat> list5 = floor.getFlats();
	    	
	    	list5.stream().map(Flat::getFlatNumber).forEach(System.out::println);
	    	System.out.println("afetr removing flats on floor 2 ");
	    }
	    floors.clear(); // Clear the floors associated with the building

	    // Remove the building from the facility
	    buildings.remove(buildingToDelete);

	    // Save the updated facility
	    facilityRepository.save(existingFacility);

	    System.out.println("Building deleted successfully");
	}*/
	

/*	@Override
	@Transactional
	public void deleteBuilding(Long facilityId, Long buildingId) {

		System.out.println("FacilityServiceImp 3");
		  Facility existingFacility = facilityRepository.findById(facilityId)
		            .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + facilityId));
		    
		  List<Building> buildings = existingFacility.getBuilding(); // Assuming it returns a List<Building>

		  buildings.stream()
		           .map(Building::getBuildingName) // Extract names using method reference
		           .forEach(System.out::println); // Print each name
		  System.out.println("FacilityServiceImp 4 -"+ existingFacility);
		  
		    // Find the building to be deleted
		    Building buildingToDelete = existingFacility.getBuilding().stream()
		            .filter(building -> building.getBuildingId().equals(buildingId))
		            .findFirst()
		            .orElseThrow(() -> new ResourceNotFoundException("Building not found with id: " + buildingId));
		    
		    System.out.println("FacilityServiceImp 5 -"+ buildingToDelete.getBuildingName());
		    
		    // Set flat reference in Meter to null
		    for (Floor floor : buildingToDelete.getFloor()) {
		        for (Flat flat : floor.getFlats()) {
		            for (Meter meter : flat.getMeters()) {
		                meter.setFlat(null);
		                meterRepository.save(meter);
		            }
		        }
		    }

		    // Remove the floors associated with the building
		    List<Floor> floors = buildingToDelete.getFloor();
		    for (Floor floor : floors) {
		        floor.getFlats().clear(); // Clear the flats associated with each floor
		    }
		    floors.clear(); // Clear the floors associated with the building

		    // Remove the building from the facility
		    buildings.remove(buildingToDelete);
		    
		    // Save the updated facility
		    facilityRepository.save(existingFacility);
		    
		    System.out.println("Building deleted successfully");
	}
	*/
}
