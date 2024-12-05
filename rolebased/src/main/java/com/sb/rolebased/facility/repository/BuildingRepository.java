package com.sb.rolebased.facility.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sb.rolebased.facility.Entity.Building;
import com.sb.rolebased.facility.Entity.Facility;
import com.sb.rolebased.facility.dtos.BuildingListDto;
import com.sb.rolebased.facility.dtos.BuildingListWithFlatAndMeter;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {

	@Query("SELECT new com.sb.rolebased.facility.dtos.BuildingListDto(b.buildingId, b.buildingName) " +
	           "FROM Building b " +
	           "WHERE b.facility.id = :facilityId")
	    List<BuildingListDto> findByFacility(@Param("facilityId") String facilityId);

	List<Building> findByFacilityFacilityId(String facilityId);

	
	@Query("SELECT NEW com.sb.rolebased.facility.dtos.BuildingListWithFlatAndMeter(b.buildingId, b.buildingName, COUNT(DISTINCT t), COUNT(DISTINCT m)) " +
	        "FROM Building b " +
	        "LEFT JOIN Floor f ON f.building.buildingId = b.buildingId " +
		      
		  	"LEFT JOIN Flat t ON t.floor.floorId = f.floorId " +

	        "LEFT JOIN Meter m ON m.flat.flatId = t.flatId " +

	        "WHERE b.facility.facilityId = :facilityId " +
	        "GROUP BY b.buildingId ")
	List<BuildingListWithFlatAndMeter> findBuildingListTotalFlatsTotalMeters(@Param("facilityId") String facilityId);

	Building findByBuildingId(Long buildingId);

	Optional<Building> findByBuildingNameAndFacility_FacilityId(String buildingName, String facilityId);

}
