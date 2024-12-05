package com.sb.rolebased.billing.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
public class Billinfo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billInfoId;
	private Long billNumber;	
	private Long consumerNumber;
	private String consumerName;	
	private Long contact;
	private String email;
	private LocalDate prvReadDate;	
	private LocalDate crtReadDate;	
	private LocalDate statementDate;
	private LocalDate dueDate;
	private String address;	
	private int lstRead;
	private int crtRead;
	private int totalUnits;	
	private double unitRate;
	private double previousBalance;
	private double currentAmount;
	private double totalAmount;
	private String meterTypeName;
	private Long flatNumber;
    private Long meterNumber;
	
	
}
