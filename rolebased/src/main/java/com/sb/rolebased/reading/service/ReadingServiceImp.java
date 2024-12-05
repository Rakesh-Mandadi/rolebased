package com.sb.rolebased.reading.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.rolebased.facility.Entity.Flat;
import com.sb.rolebased.facility.exceptionhandling.ResourceNotFoundException;
import com.sb.rolebased.facility.repository.FacilityRepository;
import com.sb.rolebased.meter.entity.Meter;
import com.sb.rolebased.meter.entity.MeterType;
import com.sb.rolebased.meter.repository.MeterRepository;
import com.sb.rolebased.meter.repository.MeterTypeRepository;
import com.sb.rolebased.reading.dto.BillDataDTO;
import com.sb.rolebased.reading.dto.DcuReadDto;
import com.sb.rolebased.reading.dto.DcuReadingDto;
import com.sb.rolebased.reading.entity.DcuRead;
import com.sb.rolebased.reading.repository.ReadingRepository;
import com.sb.rolebased.reading.utility.ReadingUtility;

@Service
public class ReadingServiceImp implements ReadingService {

	@Autowired
	ReadingRepository readingRepository;
	
	@Autowired
	MeterRepository meterRepository;
	
	@Autowired
	MeterTypeRepository meterTypeRepository;
	
	@Autowired
	FacilityRepository facilityRepository;
	
	/*@Override
	public DcuReadDto getMeterReading(DcuReadingDto dcuReadingDto) {
		
		if(dcuReadingDto.meterNumber != null) {
		 
			Optional<Meter> findByMeterNumber = meterRepository.findByMeterNumber(dcuReadingDto.meterNumber);
			
			if(findByMeterNumber.get() != null) {
				
				Meter meter = findByMeterNumber.get();
				String macAdd = meter.getMacAdd();
				
				System.out.println("macAdd"+ macAdd);
				DcuRead findByMacAdd = readingRepository.findByMacAdd(macAdd);
				
				System.out.println("Dcu Read" + findByMacAdd.getMacAdd());
				
				Flat flat = meter.getFlat();
				
				DcuReadDto convertDcuReadToDcuReadDto = ReadingUtility.convertDcuReadToDcuReadDto(findByMacAdd, meter, flat);
				
				return convertDcuReadToDcuReadDto;
			}
			
			return null;
	}
		return null;
	}
	*/


	@Override
	public List<DcuReadDto> getMeterReadingByMeterType(String facilityId, String meterType) {
		
		boolean existsById = facilityRepository.existsById(facilityId);
		
		if(existsById) {
			Optional<List<MeterType>> findAllByFacilityId = meterTypeRepository.findAllByFacilityId(facilityId);
			
			List<Long> meterTypeIds = findAllByFacilityId.orElse(List.of()).stream()
		            .filter(mType -> mType.getMeterCat() != null && mType.getMeterCat().equals(meterType))
		            .map(MeterType::getId)
		            .collect(Collectors.toList());
			
			
			List<Meter> meters = meterRepository.findAllByMeterTypeIn(meterTypeIds);
	        
	        List<String> macAddresses = meters.stream()
	            .map(Meter::getMacAdd)
	            .collect(Collectors.toList());

	         List<DcuRead> dcuReads = readingRepository.findAllByMacAddIn(macAddresses);
	         
	       return  ReadingUtility.convertDcuReadToDcuReadDto(dcuReads, meters);
		}
		return null;
	}

	  @Override
	    public List<BillDataDTO> getGenrtBillData(String facilityId, Long buildingId, Long meterTypeId) {
	        List<Object[]> results = meterRepository.getBillDataNative(facilityId, buildingId, meterTypeId);
	        return results.stream()
	            .map(this::convertToBillDataDTO)
	            .collect(Collectors.toList());
	    }
	
	private BillDataDTO convertToBillDataDTO(Object[] row) {
        return new BillDataDTO(
            ((Number) row[0]).longValue(),  // flatNumber
            ((Number) row[1]).longValue(),  // meterNumber
            ((Number) row[2]).intValue(),   // totalUnits
            ((Number) row[3]).doubleValue() // amount
        );
    }


	

}
