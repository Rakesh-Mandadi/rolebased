package com.sb.rolebased.security.services;

import java.util.List;

import com.sb.rolebased.facility.Entity.Facility;
import com.sb.rolebased.facility.dtos.FacilityDto;
import com.sb.rolebased.security.dto.CreatFacilityBySuperAdmin;
import com.sb.rolebased.security.dto.FacilityDTO;
import com.sb.rolebased.security.dto.JoinFacilitySubAdmin;
import com.sb.rolebased.security.dto.SuperAdNumData;

public interface FacilityBySuperAdminService {

	//CreatFacilityBySuperAdmin addFacilitySubAdmin(CreatFacilityBySuperAdmin creatFacilityBySuperAdmin);

	Facility addFacility(FacilityDto facilityDto);

	JoinFacilitySubAdmin linkFacilitySubAdmin(JoinFacilitySubAdmin joinFacilitySubAdmin);

	List<FacilityDTO> getUnAssignedFacilityList();

	SuperAdNumData getNumericData();
	
	String removeFacility(String facilityId);

}
