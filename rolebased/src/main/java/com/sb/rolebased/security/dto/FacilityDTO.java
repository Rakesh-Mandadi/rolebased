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
public class FacilityDTO {

	private String facilityId;
    private String facilityName;
    private String street;
	private String city;
	private String state;
	private int pin;
	private String country;
	
	private int maxBuilding;
	private int maxFloorPerBuilding;
	private int maxFlatPerFloor;
	
	public FacilityDTO(String facilityId, String facilityName) {
		super();
		this.facilityId = facilityId;
		this.facilityName = facilityName;
	}
    
	public FacilityDTO(String facilityId, String facilityName, String street, String city, String state, int pin, String country) {
		super();
		this.facilityId = facilityId;
		this.facilityName = facilityName;
		this.street = street;
		this.city = city;
		this.state = state;
		this.pin = pin;
		this.country = country;
	}
}
