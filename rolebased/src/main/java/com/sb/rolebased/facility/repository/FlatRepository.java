package com.sb.rolebased.facility.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sb.rolebased.facility.Entity.Flat;
import com.sb.rolebased.facility.dtos.FlatDto;
import com.sb.rolebased.facility.dtos.FlatListDto;

@Repository
public interface FlatRepository extends JpaRepository<Flat, Long> {

	
	 @Query("SELECT new com.sb.rolebased.facility.dtos.FlatListDto(f.flatId, f.flatNumber) " +
	           "FROM Flat f " +
	           "WHERE f.floor.floorId = :floorId")
	    List<FlatListDto> findByFloorId(@Param("floorId") Long floorId);

	    List<Flat> findByFloorFloorIdIn(List<Long> floorIds);
	 
//	  List<Flat> findByBuildingId(Long buildingId);
	
	
//	  @Query("SELECT new com.sb.rolebased.facility.dtos.FlatDto(f.flatId, f.flatNumber, COUNT(m)) " +
//	           "FROM Flat f LEFT JOIN f.meter m GROUP BY f.flatId")
	  
	
	
//	 List<Flat> findByFloorIds(Long floorId);

//	List<FlatDto> findAllFlats();
}
