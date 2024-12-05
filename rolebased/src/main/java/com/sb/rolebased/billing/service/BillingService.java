package com.sb.rolebased.billing.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;

import com.sb.rolebased.billing.dto.BillStatus;
import com.sb.rolebased.billing.entity.Billinfo;

public interface BillingService {


	Map<Long, Double> genrateBill(String facilityId, List<Long> meterNumber);

	List<BillStatus> getBillStatus(String facilityId, Long buildingId, Long meterTypeId);

	Billinfo getBillInfo(String facilityId, Long billNumber);

}
