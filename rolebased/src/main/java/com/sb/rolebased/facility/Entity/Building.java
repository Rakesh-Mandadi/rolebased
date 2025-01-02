package com.sb.rolebased.facility.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "building")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Building {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long buildingId;
	private String buildingName;
	
	@ManyToOne
	@JsonBackReference
	
	private Facility facility;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "building", orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonBackReference
	private List<Floor> floor;
	 
//	private List<Flat> flats;
	
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "building", orphanRemoval = true, fetch = FetchType.EAGER)
//	@JsonManagedReference
//	private List<Floor> floor;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "building")
//	@JsonBackReference    // to handle loop but it stop to return the saved data 
//	private List<Floor> floor;
	
//	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "building")
//	@JsonBackReference
//	private List<Floor> floor;
}
