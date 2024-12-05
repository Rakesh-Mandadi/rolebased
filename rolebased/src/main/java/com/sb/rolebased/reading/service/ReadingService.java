package com.sb.rolebased.reading.service;

import java.util.List;

import com.sb.rolebased.reading.dto.BillDataDTO;
import com.sb.rolebased.reading.dto.DcuReadDto;
import com.sb.rolebased.reading.dto.DcuReadingDto;

public interface ReadingService {

	 // DcuReadDto getMeterReading(DcuReadingDto dcuReadingDto);

	List<DcuReadDto> getMeterReadingByMeterType(String facilityId, String meterType);

	List<BillDataDTO> getGenrtBillData(String facilityId, Long buildingId, Long meterTypeId);

}
