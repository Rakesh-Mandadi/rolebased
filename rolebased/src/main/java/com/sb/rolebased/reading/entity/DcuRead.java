package com.sb.rolebased.reading.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class DcuRead {

	@Id
	private Long readId;
	
	private String macAdd;
	
	private String dcuId;
	
	private int lstRead;
	
	private int crtRead;
	
	private LocalDate dateOfReading;
	
	private LocalDate prvsDateOfReading;
}
