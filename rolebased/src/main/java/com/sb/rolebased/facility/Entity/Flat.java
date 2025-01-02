package com.sb.rolebased.facility.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sb.rolebased.meter.entity.Meter;
import com.sb.rolebased.usermanagment.entity.UserRole;

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
@Table(name = "flat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Flat {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long flatId;
	    private Long flatNumber;
	 
//	    private String username;

	    @ManyToOne
	    @JsonBackReference
	    private Floor floor;
	    
	    @OneToMany(mappedBy = "flat", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonManagedReference
	    private List<Meter> meter;
//	    
//	    @ManyToOne
//	    @JoinColumn(name = "user_role_id")  // Links to UserRole
//	    private UserRole userRole;




//	    @OneToMany(mappedBy = "flat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	    @JsonManagedReference // Use this annotation to manage the relationship
//	    private List<Meter> meters;
	    
//	    @OneToMany(mappedBy = "flat", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//	    @JsonManagedReference
//	    private List<Meter> meters;
}

