package com.sb.rolebased.facility.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sb.rolebased.facility.Entity.Floor;
import com.sb.rolebased.facility.dtos.FloorListDto;

import jakarta.websocket.server.PathParam;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {

	 @Query("SELECT new com.sb.rolebased.facility.dtos.FloorListDto(f.floorId, f.floorNumber) " +
	           "FROM Floor f " +
	           "WHERE f.building.buildingId = :buildingId")
	    List<FloorListDto> findByBuildingId(@Param("buildingId") Long buildingId);

	List<Floor> findByBuildingBuildingIdIn(List<Long> buildingIds);
}
