package com.sb.rolebased.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FacilityUserNameDto {

	private String facilityId;
    private String facilityName;
    private String city;
    
    private String userName;
    private Long userId;
    private String email;
}
