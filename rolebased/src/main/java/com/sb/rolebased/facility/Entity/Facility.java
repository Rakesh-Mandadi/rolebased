package com.sb.rolebased.facility.Entity;

import java.util.List;
import java.util.UUID;

import org.hibernate.id.Assigned;

import com.fasterxml.jackson.annotation.JsonManagedReference;
//import com.sb.rolebased.facility.dtos.FacilityDto;
import com.sb.rolebased.meter.entity.Meter;
import com.sb.rolebased.meter.entity.AssignedMeter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "facility")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Facility {	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	private long AssignedMeter;
	private long UnAssignedMeter;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "facility")
	@JsonManagedReference 
	private List<Building> building;
	
	@OneToMany(mappedBy = "facility")
    private List<Meter> meters;
	
	@OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AssignedMeter> assignedMeter;
    
}
