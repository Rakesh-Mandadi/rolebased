package com.sb.rolebased.facility.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sb.rolebased.facility.Entity.Facility;
import com.sb.rolebased.security.dto.FacilityDTO;
import com.sb.rolebased.security.dto.SuperAdNumData;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, String> {

//	 @Query("SELECT new com.sb.rolebased.security.dto.FacilityDTO(f.facilityId, f.facilityName) FROM Facility f WHERE f.facilityId NOT IN (SELECT j.facilityId FROM JoinFacilitySubAdmin j)")
//	    List<FacilityDTO> findFacilityListByUnassignedSubAdmin();

	 @Query("SELECT new com.sb.rolebased.security.dto.FacilityDTO(f.facilityId, f.facilityName) FROM Facility f WHERE f.facilityId NOT IN (SELECT CAST(j.facilityId AS string) FROM JoinFacilitySubAdmin j)")
	 List<FacilityDTO> findFacilityListByUnassignedSubAdmin();
	 
	 @Query("SELECT new com.sb.rolebased.security.dto.FacilityDTO(f.facilityId, f.facilityName, f.street, f.city, f.state, f.pin, f.country, f.maxBuilding, f.maxFloorPerBuilding, f.maxFlatPerFloor) " +
	           "FROM Facility f " +
	           "WHERE f.facilityId IN " +
	           "(SELECT jf.facilityId FROM JoinFacilitySubAdmin jf WHERE jf.subAdminId = :userId)")
	    FacilityDTO findFacilityByUserId  (@Param("userId") Long  userId);

	 @Query("SELECT new com.sb.rolebased.security.dto.FacilityDTO(f.facilityId, f.facilityName, f.street, f.city, f.state, f.pin, f.country)" +
			 	"FROM Facility f ")
	List<FacilityDTO> findFacility();

	 @Query("SELECT new com.sb.rolebased.security.dto.SuperAdNumData(" +
	           "COUNT(f), " +
	           "(SELECT COUNT(sa) FROM JoinFacilitySubAdmin sa), " +
	           "(SELECT COUNT(fl) FROM Flat fl), " +
	           "(SELECT COUNT(m) FROM Meter m)) " +
	           "FROM Facility f")
	 SuperAdNumData findNumericData();

}
