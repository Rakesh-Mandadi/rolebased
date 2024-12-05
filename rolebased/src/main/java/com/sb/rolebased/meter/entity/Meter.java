package com.sb.rolebased.meter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sb.rolebased.facility.Entity.Facility;
import com.sb.rolebased.facility.Entity.Flat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "meter")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meterId;
    
    @ManyToOne
    @JoinColumn(name = "flat_id")
    @JsonBackReference // Use this annotation to prevent infinite recursion
    private Flat flat;

    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = false)
    @JsonBackReference
    private Facility facility; // Add this field for mapping
    private Long meterType;
    private Long meterNumber;
    private String macAdd;
    private String ieeeAdd;
     private boolean status;
     
     private boolean dueStatus;
     private int lstRead;
     private Long billNumber;	

}

	
