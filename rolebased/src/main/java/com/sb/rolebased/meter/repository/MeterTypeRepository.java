package com.sb.rolebased.meter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sb.rolebased.meter.entity.MeterType;

@Repository
public interface MeterTypeRepository extends JpaRepository<MeterType, Long> {

	boolean existsByMeterTypeNameAndFacilityId(String meterTypeName, String facilityId);

	Optional<List<MeterType>> findAllByFacilityId(String facilityId);
	
	Optional<List<MeterType>> findByFacilityIdAndMeterCat(String facilityId, String meterCat);

}
