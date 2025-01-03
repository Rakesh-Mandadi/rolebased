package com.sb.rolebased.meter.entity;

import com.sb.rolebased.facility.Entity.Facility;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "assigned_meter")
public class AssignedMeter {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @Column(nullable = false)
    private String meterId;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
    
    @Column(nullable = false)
    private boolean isActive;
}

