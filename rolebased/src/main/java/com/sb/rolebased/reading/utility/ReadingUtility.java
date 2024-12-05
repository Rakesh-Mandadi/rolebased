package com.sb.rolebased.reading.utility;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sb.rolebased.facility.Entity.Flat;
import com.sb.rolebased.meter.entity.Meter;
import com.sb.rolebased.reading.dto.DcuReadDto;
import com.sb.rolebased.reading.entity.DcuRead;

public class ReadingUtility {

	public static List<DcuReadDto> convertDcuReadToDcuReadDto(List<DcuRead> dcuReads, List<Meter> meters) {
		 Map<String, Meter> meterMap = meters.stream()
			        .collect(Collectors.toMap(Meter::getMacAdd, meter -> meter));

			    return dcuReads.stream()
			        .map(dcuRead -> {
			            Meter meter = meterMap.get(dcuRead.getMacAdd());
			            DcuReadDto dto = new DcuReadDto();
			            System.out.println(dcuRead.getDcuId());
			            dto.setDcuId(dcuRead.getDcuId());
			            dto.setLstRead(dcuRead.getLstRead());
			            dto.setCrtRead(dcuRead.getCrtRead());
			            if (meter != null) {
			                dto.setMeterNumber(meter.getMeterNumber());
			                if (meter.getFlat() != null) {
			                    dto.setFlatNum(meter.getFlat().getFlatNumber());
			                }
			            }
			            return dto;
			        })
			        .collect(Collectors.toList());
	}
}
