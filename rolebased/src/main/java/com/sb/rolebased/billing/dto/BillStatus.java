package com.sb.rolebased.billing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BillStatus {

	public Long billNumber;
	public Long flatNumber;
	public double totalAmount;
	public boolean dueStatus;
}
