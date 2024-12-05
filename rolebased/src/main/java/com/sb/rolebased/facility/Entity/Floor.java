package com.sb.rolebased.facility.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "floor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Floor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long floorId;
	private int floorNumber;
	private int totalFlat;
	private int startFlatN;
	private int lastFlatN;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "floor", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Flat> flats;
		
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "floor")
//	private List<Flat> flats;
	
//	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "floor")
//	private List<Flat> flats;

	
	@ManyToOne
	private Building building;
	
	
//	@ManyToOne
//	@JsonBackReference
//	private Building building;
}
