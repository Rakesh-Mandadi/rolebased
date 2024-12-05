package com.sb.rolebased.security.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fac_subadmin")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreatFacilityBySuperAdmin {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String facilityId;
	
	private String facilityName;
	
	private int subAdminId;
}
