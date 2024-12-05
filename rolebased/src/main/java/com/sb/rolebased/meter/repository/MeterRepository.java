package com.sb.rolebased.meter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sb.rolebased.meter.entity.Meter;
import com.sb.rolebased.reading.dto.BillDataDTO;

@Repository
public interface MeterRepository extends JpaRepository<Meter, Long> {


	boolean existsByMeterNumber(Long meterNumber);

	
	 @Query("SELECT m.meterType, COUNT(m) > 0 FROM Meter m WHERE m.facility.facilityId = :facilityId AND m.flat IS NULL GROUP BY m.meterType")
	    List<Object[]> findUnassignedMetersByFacilityIdAndMeterType(@Param("facilityId") String facilityId);

	    @Query("SELECT m FROM Meter m WHERE m.facility.facilityId = :facilityId AND m.flat IS NULL")
	    List<Meter> findMetersByFacilityIdAndFlatIsNull(@Param("facilityId") String facilityId);
	    
	List<Meter> findByFlatIsNull();

	List<Meter> findAllByFacilityFacilityId(String facilityId);


	List<Meter> findByFlatFlatIdIn(List<Long> flatIds);


	    @Modifying
	    @Query("UPDATE Meter m SET m.flat = null WHERE m.flat.flatId = :flatId")
	    void setFlatToNullForFlatId(@Param("flatId") Long flatId);


	    
		Optional<Meter> findByMeterNumber(Long meterNumber);
		
		List<Meter> findAllByMeterTypeIn(List<Long> meterTypeIds);

		@Query(value = "SELECT f.flat_number, m.meter_number, " +
			       "(d.crt_read - d.lst_read) as total_units, " +
			       "((d.crt_read - d.lst_read) * mt.unit_rate) as amount " +
			       "FROM meter m " +
			       "JOIN flat f ON m.flat_id = f.flat_id " +
			       "JOIN floor fl ON f.floor_floor_id = fl.floor_id " +
			       "JOIN building b ON fl.building_building_Id = b.building_id " +
			       "JOIN facility fac ON m.facility_id = fac.facility_id " +
			       "JOIN dcu_read d ON d.mac_add = m.mac_add " +
			       "JOIN metertype mt ON mt.id = m.meter_type " +
			       "WHERE fac.facility_id = :facilityId " +
			       "AND b.building_id = :buildingId " +
			       "AND m.meter_type = :meterTypeId", 
			       nativeQuery = true)
			List<Object[]> getBillDataNative(@Param("facilityId") String facilityId, 
			                                 @Param("buildingId") Long buildingId, 
			                                 @Param("meterTypeId") Long meterTypeId);


		List<Meter> findByMeterNumberIn(List<Long> meterNumber);


		 @Query("SELECT m.meterNumber " +
		           "FROM Meter m " +
		           "JOIN m.flat f " +
		           "JOIN f.floor fl " +
		           "JOIN fl.building b " +
		           "JOIN m.facility fac " +
		           "WHERE fac.facilityId = :facilityId " +
		           "AND b.buildingId = :buildingId " +
		           "AND m.meterType = :meterTypeId")
		    List<Long> getMeterListByBuildingAndMeterType(@Param("facilityId") String facilityId, 
		                                                  @Param("buildingId") Long buildingId, 
		                                                  @Param("meterTypeId") Long meterTypeId);
		
		
		//void findAllByMeterNumberIn(List<Long> meterNumber);
}
