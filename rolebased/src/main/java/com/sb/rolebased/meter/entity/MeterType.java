package com.sb.rolebased.meter.entity;

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
@Table(name = "metertype")
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@Builder
public class MeterType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String meterCat;
	
    private String meterTypeName;
    
    private double unitRate;
    
    private String facilityId;
}
