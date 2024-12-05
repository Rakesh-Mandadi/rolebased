package com.sb.rolebased.billing.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.sb.rolebased.billing.dto.BillStatus;
import com.sb.rolebased.billing.entity.Billinfo;
import com.sb.rolebased.billing.repository.BillinfoRepository;
import com.sb.rolebased.facility.Entity.Facility;
import com.sb.rolebased.facility.repository.FacilityRepository;
import com.sb.rolebased.facility.repository.FlatRepository;
import com.sb.rolebased.meter.entity.Meter;
import com.sb.rolebased.meter.entity.MeterType;
import com.sb.rolebased.meter.repository.MeterRepository;
import com.sb.rolebased.meter.repository.MeterTypeRepository;
import com.sb.rolebased.reading.entity.DcuRead;
import com.sb.rolebased.reading.repository.ReadingRepository;


@Service
public class BillingServiceImp implements BillingService {

	@Autowired
	FacilityRepository facilityRepository;
	
	@Autowired
	MeterRepository meterRepository;
	
	@Autowired
	ReadingRepository readingRepository;
	
	@Autowired
	FlatRepository flatRepository;
	
	@Autowired
	MeterTypeRepository meterTypeRepository;
	
	@Autowired
	BillinfoRepository billinfoRepository;
	
	private Long baseBillNumber= 0000001000L;
	
	
	public Map<Long, Double> genrateBill(String facilityId, List<Long> meterNumber) {
	boolean existsById = facilityRepository.existsById(facilityId);
		
		if(existsById) {
			List<Meter> meters = meterRepository.findByMeterNumberIn(meterNumber);
			
		//	findByMeterNumberIn.stream().map(this::performOnMeter).colle
			
			 List<Billinfo> billinfos = meters.stream()
                                        .map(this::performOnMeter)
                                        .collect(Collectors.toList());
			 
			 List<Billinfo> saveAllAndFlush = billinfoRepository.saveAllAndFlush(billinfos); 
			 
			 Map<Long, Long> activeBill = new HashMap<>(); 
		//	 List<Long> meterN = new ArrayList<>();
			 
			 Map<Long, Double> billAndAmount = new HashMap<>();
			 saveAllAndFlush.stream().forEach(saf -> {billAndAmount.put(saf.getBillNumber(), saf.getTotalAmount());
					 									activeBill.put(saf.getMeterNumber(), saf.getBillNumber());
		//			 									meterN.add(saf.getBillNumber());
					 									});
			 
			 
			 List<Meter> findByMeterNumberIn = meterRepository.findByMeterNumberIn(meterNumber);
			 
			 findByMeterNumberIn.stream().forEach(mt ->{
				 											Long billN= activeBill.get(mt.getMeterNumber());
				 											if(billN != null) {
				 												mt.setBillNumber(billN);
				 											}
			 											} );
			 meterRepository.saveAll(findByMeterNumberIn);
			 
			 
		//	 (Map.Entry<Long, Long> entry : meterToBillNumberMap.entrySet()) {
		//            Long meterNumber = entry.getKey();
		//            Long billNumber = entry.getValue();
			 
//			 for(Map.Entry<Long, Long> entry : activeBill.entrySet()) {
//				 Long mn = entry.getKey();
//				 Long bn = entry.getValue();
//				 
//				 
//			 }
			 
			 return billAndAmount;
		}
		 else {
	            throw new RuntimeException("Facility not found with id: " + facilityId);
	        }
	}
	
	public Billinfo performOnMeter(Meter meter) {
		
		DcuRead dcuRead = readingRepository.findByMacAdd(meter.getMacAdd());
		
		//Long billNumber = generateBillNumber();
		
		Long consumerNumber = 889977L;
		String consumerName = "SmartBuild Employee";
		Long contact = 9988776655L;
		String email = "smart@build.com";
		
		int crtRead = dcuRead.getCrtRead();
		int lstRead = dcuRead.getLstRead();
		int totalUnits = crtRead-lstRead;
		
		LocalDate dateOfReading = dcuRead.getDateOfReading();
		LocalDate prvsDateOfReading = dcuRead.getPrvsDateOfReading();
		LocalDate stmtDate = LocalDate.now();
		LocalDate dueDate = stmtDate.plusDays(20);
		
			Optional<MeterType> meterType = meterTypeRepository.findById(meter.getMeterType()); 					
			MeterType mTy = meterType.get();
			String meterTypeName = mTy.getMeterTypeName();
			double unitRate = mTy.getUnitRate();
		
		double currentAmount = totalUnits*unitRate;
		double previousBalance = 0.0;
		double totalAmount = currentAmount + previousBalance ;
		
		
		Long meterNumber = meter.getMeterNumber();	
		Long flatNumber = meter.getFlat().getFlatNumber();
		 String buildingName = meter.getFlat().getFloor().getBuilding().getBuildingName();
		 	Facility facility = meter.getFlat().getFloor().getBuilding().getFacility();
			 	String facilityName = facility.getFacilityName();
			 	String street = facility.getStreet();
			 	String city = facility.getCity();
			 	String country = facility.getCountry();
			 	int pin = facility.getPin();
		 						
		
		
		return Billinfo.builder()
				.billNumber(generateBillNumber())
				.consumerNumber(consumerNumber)
				.consumerName(consumerName)
				.contact(contact)
				.email(email)
				.prvReadDate(prvsDateOfReading)
				.crtReadDate(dateOfReading)
				.statementDate(stmtDate)
				.dueDate(dueDate)
				.address(flatNumber + " "+ buildingName + "Block, " + facilityName+ ", "+ street +", "+ city+ ", "+ pin )
				.lstRead(lstRead)
				.crtRead(crtRead)
				.totalUnits(totalUnits)
				.unitRate(unitRate)
				.previousBalance(previousBalance)
				.currentAmount(currentAmount)
				.totalAmount(totalAmount)
				.meterTypeName(meterTypeName)
				.flatNumber(flatNumber)
				.meterNumber(meterNumber)
				.build();
	}
	
	public Long generateBillNumber() {
		
			return ++baseBillNumber;
	}

	@Override
	public List<BillStatus> getBillStatus(String facilityId, Long buildingId, Long meterTypeId) {
			boolean existsById = facilityRepository.existsById(facilityId);
		
			if(existsById) {
				
				List<BillStatus> listBillStatus = new ArrayList<>();
				
			//	List<Meter> meters = meterRepository.findByMeterNumberIn(meterNumber);
				
				System.out.println("getBillStatus 1");
				List<Long> billDataNative = meterRepository.getMeterListByBuildingAndMeterType(facilityId, buildingId, meterTypeId);
				
				System.out.println("getBillStatus 2 "+ billDataNative);
				List<Meter> meters = meterRepository.findByMeterNumberIn(billDataNative);
				
				List<Long> billNList = meters.stream().map(mt -> mt.getBillNumber()).collect(Collectors.toList());
				
				Map<Long, Boolean> mtrAndDueStatusMap = new HashMap<>();	 
				
				
				meters.forEach(mt -> {mtrAndDueStatusMap.put(mt.getBillNumber(), mt.isDueStatus() );});
				
				List<Billinfo> bills = billinfoRepository.findByBillNumberIn(billNList);
				
				bills.stream().forEach(b ->  {
													BillStatus bs= new BillStatus();
												Boolean boolean1 = mtrAndDueStatusMap.get(b.getMeterNumber());
												System.out.println("boolen 1 value "+ boolean1);
												bs.setDueStatus(true);
												bs.setBillNumber(b.getBillNumber());
												bs.setFlatNumber(b.getFlatNumber());
												bs.setTotalAmount(b.getTotalAmount());
												
												 listBillStatus.add(bs);
												});
				
				return listBillStatus;
			}
			else {
	            throw new RuntimeException("Facility not found with id: " + facilityId);
	        }
	}

	@Override
	public Billinfo getBillInfo(String facilityId, Long billNumber) {
		boolean existsById = facilityRepository.existsById(facilityId);
		
		if(existsById) {
			Billinfo bInfo =	billinfoRepository.findByBillNumber(billNumber);
			return bInfo;
		}
		else {
            throw new RuntimeException("Facility not found with id: " + facilityId);
        }
		
	}

	

}
