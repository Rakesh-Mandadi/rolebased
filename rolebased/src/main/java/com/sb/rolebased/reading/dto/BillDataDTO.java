package com.sb.rolebased.reading.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDataDTO {
    private Long flatNumber;
    private Long meterNumber;
    private int totalUnits;
    private double amount;
}