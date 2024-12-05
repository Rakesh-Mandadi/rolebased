package com.sb.rolebased.security.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.rolebased.facility.Entity.Facility;
import com.sb.rolebased.facility.Entity.Flat;
import com.sb.rolebased.facility.dtos.FacilityDto;
import com.sb.rolebased.facility.exceptionhandling.FacilityNotFoundException;
import com.sb.rolebased.facility.repository.FacilityRepository;
import com.sb.rolebased.facility.repository.FlatRepository;
import com.sb.rolebased.meter.repository.MeterRepository;
import com.sb.rolebased.security.dto.FacilityDTO;
import com.sb.rolebased.security.dto.JoinFacilitySubAdmin;
import com.sb.rolebased.security.dto.SuperAdNumData;
import com.sb.rolebased.security.repository.JoinFacilitySubAdminRepository;

@Service
public class FacilityBySuperAdminServiceImp implements FacilityBySuperAdminService {
	
	@Autowired
	FacilityRepository facilityRepository;
	
	@Autowired
	JoinFacilitySubAdminRepository joinFacilitySubAdminRepository;

	@Autowired
	FlatRepository flatRepository;
	
	@Autowired
	MeterRepository meterRepository;

	@Override
	public Facility addFacility(FacilityDto facilityDto) {
			System.out.println("FacilityBySuperAdminServiceImp 1");
			System.out.println("ghtb  " + facilityDto.getFacilityId());
			System.out.println(facilityDto.getFacilityName());
			System.out.println(facilityDto.getStreet());
			System.out.println(facilityDto.getCity());
			System.out.println(facilityDto.getState());
			System.out.println(facilityDto.getPin());
			System.out.println(facilityDto.getCountry());
			
			
			 Facility facility = Facility.builder()
					.facilityName(facilityDto.getFacilityName())
					.street(facilityDto.getStreet())
					.city(facilityDto.getCity())
					.state(facilityDto.getState())
					.pin(facilityDto.getPin())
					.country(facilityDto.getCountry())
	//				.building(facilityDto.getBuildingDto())
					.facilityId(generateFacilityId(facilityDto))
					.building(null)
					.build();

		        
		        System.out.println("FacilityBySuperAdminServiceImp 2");
		        
			 Facility save = facilityRepository.save(facility);
			 
			 return save;
		
	}
	
	 
    public  String generateFacilityId(FacilityDto facilityDto) {
    	 String facilityId = null;
    	
    	 if (facilityDto.getFacilityId() == null || facilityDto.getFacilityId().isEmpty()) {
            // Take the first 3 or 4 characters of the facility name, default to 3
            String prefix = facilityDto.getFacilityName() != null && facilityDto.getFacilityName().length() >= 3 
                ? facilityDto.getFacilityName().substring(0, 2) 
                : "FAC";

            // Generate a unique ID and append it to the prefix
            String uniquePart = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
             facilityId = prefix.toUpperCase() + uniquePart;
        }
    	System.out.println("gen facil id" + facilityId);
    	 return facilityId;

    }   


	@Override
	public JoinFacilitySubAdmin linkFacilitySubAdmin(JoinFacilitySubAdmin joinFacilitySubAdmin) {
		
		System.out.println("FacilityBySuperAdminServiceImp 3");
		System.out.println(joinFacilitySubAdmin.getFacilityName());
		JoinFacilitySubAdmin save = joinFacilitySubAdminRepository.save(joinFacilitySubAdmin);
		
		System.out.println("FacilityBySuperAdminServiceImp 4 "+ save.getFacilityName());
		
		return save; 
	}


	@Override
	public List<FacilityDTO> getUnAssignedFacilityList() {
		System.out.println("FacilityBySuperAdminServiceImp 5");
		List<FacilityDTO> findFacilityListByUnassignedSubAdmin = facilityRepository.findFacilityListByUnassignedSubAdmin();
		System.out.println("FacilityBySuperAdminServiceImp 6");
		
		return findFacilityListByUnassignedSubAdmin;
	}


	@Override
	public SuperAdNumData getNumericData() {
		return facilityRepository.findNumericData();
	}


	@Override
	public String removeFacility(String facilityId) {
		Optional<Facility> findById = facilityRepository.findById(facilityId);	
		if(findById.isPresent()) {
			Facility facility = findById.get();
			facilityRepository.delete(facility);
			return "Deleted";
		}
		else {
			throw new FacilityNotFoundException("Facility not found.. !");
		}
	}
	
}
