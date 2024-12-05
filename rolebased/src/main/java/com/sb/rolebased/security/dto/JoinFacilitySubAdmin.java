package com.sb.rolebased.security.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class JoinFacilitySubAdmin {
	
	@Id
	private String facilityId;
	
	private String facilityName;
	
	private Long subAdminId;
	
	private String subAdminUserName;
}
