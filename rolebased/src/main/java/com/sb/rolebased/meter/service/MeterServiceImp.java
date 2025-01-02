package com.sb.rolebased.meter.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.rolebased.facility.Entity.Facility;
import com.sb.rolebased.facility.Entity.Flat;
import com.sb.rolebased.facility.dtos.FlatDto;
import com.sb.rolebased.facility.exceptionhandling.FacilityNotFoundException;
import com.sb.rolebased.facility.repository.FacilityRepository;
import com.sb.rolebased.facility.repository.FlatRepository;
import com.sb.rolebased.meter.dto.MeterDto;
import com.sb.rolebased.meter.dto.MeterTpDto;
import com.sb.rolebased.meter.dto.MeterTypeDto;
import com.sb.rolebased.meter.dto.MeterTypeDto.MeterTypeDtoBuilder;
import com.sb.rolebased.meter.dto.MeterTypeStatus;
import com.sb.rolebased.meter.entity.Meter;
import com.sb.rolebased.meter.entity.MeterType;
import com.sb.rolebased.meter.exceptionhandling.MeterNumberExistException;
import com.sb.rolebased.meter.exceptionhandling.MeterTypeExistException;
import com.sb.rolebased.meter.repository.MeterRepository;
import com.sb.rolebased.meter.repository.MeterTypeRepository;
import com.sb.rolebased.meter.utills.MeterUtility;
import com.sb.rolebased.security.dto.FlatMeterLinkResponseDto;

import lombok.Builder;

@Service
public class MeterServiceImp implements MeterService {

	@Autowired
	MeterTypeRepository meterTypeRepository;
	
	@Autowired
	MeterRepository meterRepository;
	
	@Autowired
	FacilityRepository facilityRepository;
	
	@Autowired
	FlatRepository flatRepository;
	
	@Override                                                                // implement exception response latter 
	public MeterType saveMeterType(MeterTpDto meterTpDto, String facilityId) { 
		
		System.out.println("MeterServiceImp 1");		
		  if (meterTypeRepository.existsByMeterTypeNameAndFacilityId(meterTpDto.getMeterTypeName(), facilityId)) {
	            throw new MeterTypeExistException("MeterType already exists"); 
	        }	
		MeterType meterType = new MeterType();
		meterType.setMeterTypeName(meterTpDto.getMeterTypeName());
		meterType.setUnitRate(meterTpDto.getUnitRate());
		meterType.setMeterCat(meterTpDto.getMeterCat());
		meterType.setFacilityId(facilityId);
		return meterTypeRepository.save(meterType);
	}

	public List<MeterType> getListMeterType(String facilityId, String meterType) {
		
		System.out.println("MeterServiceImp  2");
//		List<MeterType> findAll = meterTypeRepository.findAll();
		Optional<List<MeterType>> findAllByUserId = meterTypeRepository.findAllByFacilityId(facilityId);
		List<MeterType> collect = findAllByUserId.orElse(List.of()).stream().filter(mType -> mType.getMeterCat() != null && mType.getMeterCat().equals(meterType)).collect(Collectors.toList());
		
		return collect;
	}
	
	@Override
	public List<MeterTypeDto> getListOfAllMeterType(String facilityId) {
		System.out.println("MeterServiceImp  2.1");
		
		Optional<List<MeterType>> findAllByFacilityId = meterTypeRepository.findAllByFacilityId(facilityId);
	    if (findAllByFacilityId.isPresent()) {
	        List<MeterType> list = findAllByFacilityId.get();

	        return list.stream()
	                   .map(meterType -> MeterTypeDto.builder()
	                                                 .id(meterType.getId())
	                                                 .meterTypeName(meterType.getMeterTypeName())
	                                                 .build())
	                   .collect(Collectors.toList());
	    } else {
	        // Handle the case when no meter types are found
	        return Collections.emptyList();
	    }

	}

	 public Meter saveMeter(MeterDto meterDto, String facilityId) {
	        System.out.println("MeterServiceImp  3");        
	        Facility facility = facilityRepository.findById(facilityId)
		            .orElseThrow(() -> new FacilityNotFoundException("Facility not found with id " + facilityId));
	        
	        System.out.println(meterDto.getMeterNumber());
	        boolean existByMeterNumber = meterRepository.existsByMeterNumber(meterDto.getMeterNumber());
	        
	        System.out.println(existByMeterNumber);
	        if (existByMeterNumber) {
	            throw new MeterNumberExistException("Meter number already exists.");
	        }

	        

	        System.out.println("MeterServiceImp  4");
	        Meter meter = MeterUtility.convertMeterDtoToMeterEntity(meterDto, facility);

	        Meter save = meterRepository.save(meter);    
	        return save;
	    }

	@Override
	public List<Meter> getListOfMeters(String facilityId) {
		
		System.out.println("MeterServiceImp  5");
		
		  List<Meter> findAll = meterRepository.findAllByFacilityFacilityId(facilityId);
		
		return findAll;
		
	}

	@Override
	public List<MeterTypeStatus> checkUnassignedMetersByMeterType(String facilityId, String meterCat) {
	    // Retrieve results from the meter repository
		List<Object[]> results = meterRepository.findUnassignedMetersByFacilityIdAndMeterType(facilityId);
	    
	    // Retrieve all meter types
	    Optional<List<MeterType>> meterTypes = meterTypeRepository.findByFacilityIdAndMeterCat(facilityId, meterCat);
	    List<MeterType> list = null;
	    if(meterTypes.isPresent()) {
	    	 list = meterTypes.get();
	    }
	    
	    // Create a map to store unassigned meter information
	    Map<Long, Boolean> unassignedMeterMap = new HashMap<>();
	    for (int i = 0; i < results.size(); i++) {
	        Object[] result = results.get(i);
	        Long meterTypeId = (Long) result[0];
	        Boolean isUnassigned = (Boolean) result[1];
	        System.out.println("Meter Type ID: " + meterTypeId + ", Is Unassigned: " + isUnassigned);
	        unassignedMeterMap.put(meterTypeId, isUnassigned);
	    }
	    
	    // Create a list to hold the status of each meter type
	    List<MeterTypeStatus> statusList = new ArrayList<>();
	    for (int i = 0; i < list.size(); i++) {
	        MeterType meterType = list.get(i);
	        boolean isAvailable = unassignedMeterMap.getOrDefault(meterType.getId(), false);
	        statusList.add(new MeterTypeStatus(meterType.getMeterTypeName(), meterType.getId(), isAvailable));
	    }
	    
	    // Return the list of meter type statuses
	    return statusList;
	}
	@Override
    public FlatMeterLinkResponseDto linkFlatsAndMeters(FlatDto flatDto, String facilityId) {
        Optional<Flat> optionalFlat = flatRepository.findById(flatDto.getFlatNumber());

        if (optionalFlat.isPresent()) {
            try {
                Flat savedFlat = optionalFlat.get();

                List<Meter> metersWithoutFlat = meterRepository.findMetersByFacilityIdAndFlatIsNull(facilityId);

                List<Long> meterTypeIds = new ArrayList<>(flatDto.getMeterTypeIds());
                int assignedMeters = 0;
                int unassignedMeters = metersWithoutFlat.size();

                for (Meter meter : metersWithoutFlat) {
                    if (meterTypeIds.contains(meter.getMeterType())) {
                        meter.setFlat(savedFlat);
                        meterTypeIds.remove(meter.getMeterType());
                        assignedMeters++;
                    }
                }

                unassignedMeters -= assignedMeters;

                meterRepository.saveAll(metersWithoutFlat);

                List<Meter> existingMeters = savedFlat.getMeter();
                existingMeters.addAll(metersWithoutFlat);

                flatRepository.save(savedFlat);

                // Return DTO with assigned and unassigned counts
                return new FlatMeterLinkResponseDto(savedFlat, assignedMeters, unassignedMeters);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

//	@Override
//	public Flat linkFlatAndMeters(FlatDto flatDto, String facilityId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
	
	
	@Override
	public Flat linkFlatAndMeters(FlatDto flatDto, String facilityId) {
	    // Fetch the flat by its ID
	    Optional<Flat> optionalFlat = flatRepository.findById(flatDto.getFlatNumber());

	    if (optionalFlat.isPresent()) {
	        try {
	            Flat savedFlat = optionalFlat.get();

	            // Retrieve meters with null flatId and matching facilityId
	            List<Meter> metersWithoutFlat = meterRepository.findMetersByFacilityIdAndFlatIsNull(facilityId);

	            // Get the list of meterTypeIds from the DTO
	            List<Long> meterTypeIds = new ArrayList<>(flatDto.getMeterTypeIds());

	            // Iterate over the meters without flat and match them with meterTypeIds
	            for (Meter meter : metersWithoutFlat) {
	                if (meterTypeIds.contains(meter.getMeterType())) {
	                    // Assign the meter to the flat
	                    meter.setFlat(savedFlat);
	                    // Remove the matched meterTypeId to prevent reassigning
	                    meterTypeIds.remove(meter.getMeterType());
	                }
	            }

	            // Save the updated meters
	            meterRepository.saveAll(metersWithoutFlat);

	            // Ensure the Flat's meters collection is not replaced
	            List<Meter> existingMeters = savedFlat.getMeter();
	            existingMeters.addAll(metersWithoutFlat);

	            // Save the flat with its updated meters
	            Flat updatedFlat = flatRepository.save(savedFlat);

	            return updatedFlat;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return null;
	}


	
	


/*
	@Override
	public Flat linkFlatAndMeters(FlatDto flatDto, Long facilityId) {
	    System.out.println("MeterServiceImp  3");

	    // Fetch the flat by its ID
	    Optional<Flat> optionalFlat = flatRepository.findById(flatDto.getFlatId());
	    System.out.println("iewrwefjk");
	    if (optionalFlat.isPresent()) {
	        try {
	            Flat savedFlat = optionalFlat.get();

	            System.out.println("iewrwefjk");
	            // Retrieve meters with null flatId and matching facilityId
	            List<Meter> metersWithoutFlat = meterRepository.findMetersByFacilityIdAndFlatIsNull(facilityId);

	            System.out.println("sdfsdf  "+ metersWithoutFlat.size());
	            // Get the list of meterTypeIds from the DTO
	            List<Long> meterTypeIds = new ArrayList<>(flatDto.getMeterTypeIds());

	          System.out.println("rehjk "+ meterTypeIds.size());
	            // List to keep track of meters that will be assigned to the flat
	            List<Meter> assignedMeters = new ArrayList<>();

	            // Iterate over the meters without flat and match them with meterTypeIds
	            for (Meter meter : metersWithoutFlat) {
	                if (meterTypeIds.contains(meter.getMeterType())) {
	                    // Assign the meter to the flat
	                    meter.setFlat(savedFlat);
	                    assignedMeters.add(meter);

	                    // Remove the matched meterTypeId to prevent reassigning
	                    System.out.println("jhfdfk "+ meter.getMeterType());
	                    meterTypeIds.remove(meter.getMeterType());
	                }
	            }

	            System.out.println("rwef "+ assignedMeters.size());
	            // Save the updated meters
	            meterRepository.saveAll(assignedMeters);

	            // Update the flat's meter list
	            savedFlat.setMeters(assignedMeters);

	            // Save the flat with its updated meters
	            Flat updatedFlat = flatRepository.save(savedFlat);

	            return updatedFlat;
	        } catch (Exception e) {
	            // Handle exception appropriately
	            e.printStackTrace();
	            // Log the exception and possibly rethrow a custom exception or return an appropriate response
	        }
	    }

	    return null;
	}
*/
   
	


}
