package com.sb.rolebased.subadmin.controller;

import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.rolebased.billing.dto.BillStatus;
import com.sb.rolebased.billing.entity.Billinfo;
import com.sb.rolebased.billing.service.BillingService;
import com.sb.rolebased.facility.Entity.Building;
import com.sb.rolebased.facility.Entity.Facility;
import com.sb.rolebased.facility.Entity.Flat;
import com.sb.rolebased.facility.dtos.BuildingDto;
import com.sb.rolebased.facility.dtos.BuildingDtoForBldTbl;
import com.sb.rolebased.facility.dtos.BuildingListDto;
import com.sb.rolebased.facility.dtos.BuildingListWithFlatAndMeter;
import com.sb.rolebased.facility.dtos.FacilityDto;
import com.sb.rolebased.facility.dtos.FlatDto;
import com.sb.rolebased.facility.dtos.FlatListDto;
import com.sb.rolebased.facility.dtos.FloorListDto;
import com.sb.rolebased.facility.dtos.SubAdNumData;
import com.sb.rolebased.facility.service.FacilityService;
import com.sb.rolebased.facility.utills.Utility;
import com.sb.rolebased.meter.dto.MeterDto;
import com.sb.rolebased.meter.dto.MeterTpDto;
import com.sb.rolebased.meter.dto.MeterTypeDto;
import com.sb.rolebased.meter.dto.MeterTypeStatus;
import com.sb.rolebased.meter.entity.Meter;
import com.sb.rolebased.meter.entity.MeterType;
import com.sb.rolebased.meter.service.MeterService;
import com.sb.rolebased.reading.dto.BillDataDTO;
import com.sb.rolebased.reading.dto.DcuReadDto;
import com.sb.rolebased.reading.dto.DcuReadingDto;
import com.sb.rolebased.reading.service.ReadingService;
import com.sb.rolebased.security.dto.FacilityDTO;
import com.sb.rolebased.security.response.SuccessResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/users")
public class SubAdminController {
	
	@Autowired
	FacilityService facilityService;
	
	@Autowired
	MeterService meterService;
	
	@Autowired
	ReadingService readingService;
	
	@Autowired
	BillingService billingService;

	@PutMapping("/updateFacilityDetails")          // facility name and Address update
	@PreAuthorize("hasAnyRole('SUBADMIN','SUPERADMIN')") 
	public ResponseEntity<?> updateFacilityDetails(@RequestBody FacilityDto facilityDto ){
		System.out.println("SubAdminController 1");
		
		Facility saveFacilityDetails = facilityService.saveFacilityDetails(facilityDto);
		
		FacilityDTO facilityDTO = Utility.convertFacilityToFacilityDTO(saveFacilityDetails);
		System.out.println("SubAdminController 2");
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<FacilityDTO>builder()
						.data(facilityDTO )
						.massage("facility details has been saved")
						.build());
	}
	
	 @GetMapping("/subadmin/{userId}")
	 @PreAuthorize("hasAnyRole('SUBADMIN','SUPERADMIN')")
	 public ResponseEntity<?> getFacilityForSubAdmin(@PathVariable Long userId) {
		
		FacilityDTO facilityForSubAdmin = facilityService.getFacilityForSubAdmin(userId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<FacilityDTO>builder()
						.data(facilityForSubAdmin)
						.massage("user facility name and Id Returened ! ")
						.build());
	}
	
	
	 @PostMapping("/{facilityId}/buildings")     // adding new building by subadmin
	    @PreAuthorize("hasRole('SUBADMIN')")
	    public ResponseEntity<SuccessResponse<Building>> addBuilding(@PathVariable String facilityId, @RequestBody BuildingDto buildingDto) {
	        System.out.println("SubAdminController 3");
	        Building createdBuilding = facilityService.createBuildings(facilityId, buildingDto);
	        return ResponseEntity.status(HttpStatus.OK)
	                .body(SuccessResponse.<Building>builder()
	                        .data(createdBuilding)
	                        .massage("Building data with floor stored")
	                        .build());
	    }
	 
	 @PutMapping("/{facilityId}/building")   // update Building 
	 @PreAuthorize("hasRole('SUBADMIN')")
	 public ResponseEntity<SuccessResponse<Building>> updateBuilding(@PathVariable String facilityId, @RequestBody BuildingListDto buildingListDto) {
	     Building updatedBuilding = facilityService.updateBuilding(facilityId, buildingListDto);
	     return ResponseEntity.status(HttpStatus.OK)
	             .body(SuccessResponse.<Building>builder()
	                     .data(updatedBuilding)
	                     .massage("Building data updated")
	                     .build());
	 }
	 
	 @DeleteMapping("/{facilityId}/{buildingId}")
	 @PreAuthorize("hasRole('SUBADMIN')")
	 public ResponseEntity<?> deleteBuilding(@PathVariable String facilityId,@PathVariable Long buildingId ){
		 
		 System.out.println("inside delet building");
		 		facilityService.deleteBuilding(facilityId, buildingId);
		 return ResponseEntity.status(HttpStatus.NO_CONTENT)
				 .body(SuccessResponse.<String>builder().data(null).massage(null).build());
	 }
	 
	/*
	@PostMapping("/{facilityId}/buildings")
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<SuccessResponse<List<Building>>> addBuilding(@PathVariable Long facilityId, @RequestBody List<BuildingDto> buildingDtoList){
			
		System.out.println("SubAdminController 3");
		List<Building> createBuildings = facilityService.createBuildings(facilityId, buildingDtoList);
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<List<Building>>builder()
						.data(createBuildings)
						.massage("building data with floor stored")
						.build());
	}
	*/
	@GetMapping("/subAdminBuilding/{facilityId}")   // list of building name and Id
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<?> getBuildingList(@PathVariable String facilityId){
		
		   System.out.println("SubAdminController 4");
		List<BuildingListDto> buildingNameList = facilityService.getBuildingNameList(facilityId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<List<BuildingListDto>>builder()
						.data(buildingNameList)
						.massage("BuildingList  ! ")
						.build());
	}
	
	
	@GetMapping("/subAdminfloor/{buildingId}")
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<?> getFloorList(@PathVariable Long buildingId){
		
		System.out.println("SubAdminController 5");
		List<FloorListDto> floorNumberList = facilityService.getFloorNumberList(buildingId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<List<FloorListDto>>builder()
						.data(floorNumberList)
						.massage("floorList  ! ")
						.build());
	}
	
	
	@GetMapping("/subAdminflat/{floorId}")
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<?> getFlatList(@PathVariable Long floorId){
		
		System.out.println("SubAdminController 6");
			List<FlatListDto> flatNumberList = facilityService.getFlatNumberList(floorId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<List<FlatListDto>>builder()
						.data(flatNumberList)
						.massage("flat List  ! ")
						.build());
	}
	
	@GetMapping("/{facilityId}/getnumericdata")
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<?> getNumericData(@PathVariable String facilityId){
				
		System.out.println("SubAdminController 7");
			SubAdNumData subAdNumData = facilityService.getSubAdNumData(facilityId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<SubAdNumData>builder()
						.data(subAdNumData)
						.massage("flat List  ! ")
						.build());
	}
	
	@GetMapping("/{facilityId}/{buildingId}/getTblDataForPrtclrBld")
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<?> getBuildingData(@PathVariable Long buildingId,@PathVariable String facilityId){
		System.out.println("SubAdminController 8");
		
		BuildingDtoForBldTbl buildingData = facilityService.getBuildingData(facilityId, buildingId);
		System.out.println("SubAdminController 9");
		return ResponseEntity.ok(buildingData);
	}
	
	/* meter section */	
	
	@PostMapping("/{facilityId}/addMeterType")
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<?> addMeterType(@PathVariable String facilityId , @RequestBody MeterTpDto meterTpDto){
		
		System.out.println("SubAdminController 4");
		MeterType saveMeterType = meterService.saveMeterType(meterTpDto,facilityId);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<MeterType>builder()
						.data(saveMeterType).massage("Succsessfully meterType created..").build());
	}
	
	@GetMapping("/{facilityId}/{meterType}")
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<?> getListOfMeterType(@PathVariable String facilityId, @PathVariable String meterType){
		
		System.out.println("SubAdminController 5 ");
		List<MeterType> listMeterType = meterService.getListMeterType(facilityId, meterType);
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<List<MeterType>>builder().data(listMeterType).massage("list of MetrType ").build());
	}
	
	@GetMapping("/{facilityId}/allMeterType")
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<?> getListOfAllMeterType(@PathVariable String facilityId){
		
		System.out.println("SubAdminController 5 ");
		List<MeterTypeDto> listMeterType = meterService.getListOfAllMeterType(facilityId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<List<MeterTypeDto>>builder().data(listMeterType).massage("list of MetrType ").build());
	}
	
	
	@PostMapping("/{facilityId}/addmeter")
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<?> addNewMeter(@PathVariable String facilityId,@RequestBody MeterDto meterDto){
		
		System.out.println("SubAdminController 6");
		Meter saveMeter = meterService.saveMeter(meterDto, facilityId);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<Meter>builder()
						.data(saveMeter).massage("New Meter Added Succsessfully").build());
	}
	
	@GetMapping("/{facilityId}/getmeterlist")
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<?> gettingListOfMeter(@PathVariable String facilityId){
		
		System.out.println("SubAdminController 6");
		List<Meter> listOfMeters = meterService.getListOfMeters(facilityId);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<List<Meter>>builder()
						.data(listOfMeters).massage("List of meters").build());
	}
	
	@GetMapping("/{facilityId}/{meterCat}/unassigned-status")
	@PreAuthorize("hasRole('SUBADMIN')")
    public ResponseEntity<List<MeterTypeStatus>> getUnassignedMeterStatus(@PathVariable String facilityId, @PathVariable String meterCat) {
         System.out.println("SubAdminController : getUnassignedMeterStatus()");
		List<MeterTypeStatus> statusList = meterService.checkUnassignedMetersByMeterType(facilityId, meterCat);
        return new ResponseEntity<>(statusList, HttpStatus.OK);
    }
	
	
	
	@PostMapping("/{facilityId}/linkflatwithMeter")
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<?> linkFlatAndMeters(@PathVariable String facilityId, @RequestBody FlatDto flatDto){
		
		System.out.println("SubAdminController 8");
		
		Flat saveFlatDetails = meterService.linkFlatAndMeters(flatDto, facilityId);
		return new ResponseEntity<>(saveFlatDetails, HttpStatus.OK);
	}
	
	@GetMapping("/{facilityId}/listOfBuildingFlat")
	@PreAuthorize("hasRole('SUBADMIN')")      // table data for Building name, total flat, total meter 
	public ResponseEntity<?> listOfBuildingFlat(@PathVariable String facilityId){
		
		System.out.println("SubAdminController 9");
		List<BuildingListWithFlatAndMeter> listOfBuildingFlat = facilityService.getListOfBuildingFlat(facilityId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<List<BuildingListWithFlatAndMeter>>builder().data(listOfBuildingFlat).massage("table data for Building, totalflat, total meters").build());
	}
	
	
	
	
	/* readings */
	
	/*
	 
	@GetMapping("/{facilityId}/getReading")
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<?>  getMeterReading(@RequestBody DcuReadingDto dcuReadingDto){
		
		System.out.println("SubAdminController getMeterReading");
		DcuReadDto meterReading = readingService.getMeterReading(dcuReadingDto);
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<DcuReadDto>builder().data(meterReading).massage("reading data").build());
	} */
	
	
	@GetMapping("/{facilityId}/getMeterType/{meterType}")
	@PreAuthorize("hasRole('SUBADMIN')")			// reading data table for gas electricity water
	public ResponseEntity<?>  getMeterReadingByMeterType(@PathVariable String facilityId, @PathVariable String meterType){
		
		System.out.println("SubAdminController getMeterReadingByMeterType 1");
		List<DcuReadDto> meterReadingByMeterType = readingService.getMeterReadingByMeterType(facilityId, meterType);
		
		System.out.println("SubAdminController getMeterReadingByMeterType 2");
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<List<DcuReadDto>>builder().data(meterReadingByMeterType).massage("Metr reding data").build());
	}
	
	
	@GetMapping("/{facilityId}/{buildingId}/{meterTypeId}")
	@PreAuthorize("hasRole('SUBADMIN')")         // Bill data table to based on Building and MeterType
	public ResponseEntity<?> getGenrtBillData(@PathVariable String facilityId, @PathVariable Long buildingId, @PathVariable Long meterTypeId){
		
		List<BillDataDTO> genrtBillData = readingService.getGenrtBillData(facilityId, buildingId, meterTypeId);
		return ResponseEntity.ok(genrtBillData);
	}
	
	
	
	/* Generate Bills */
	
	
	@PostMapping("/{facilityId}/gnrtBillByMeterNumber")
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<?> genrateBill(@PathVariable String facilityId, @RequestBody List<Long> meterNumber){
		  System.out.println("SubAdminController genrateBill()");
		Map<Long, Double> genrateBill = billingService.genrateBill(facilityId, meterNumber);	
		return ResponseEntity.ok(genrateBill);
	}
	
	
	@GetMapping("/{facilityId}/{buildingId}/{meterTypeId}/getBillStatus")
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<?> getBillStatus(@PathVariable String facilityId, @PathVariable Long buildingId, @PathVariable Long meterTypeId){
		 System.out.println("SubAdminController genrateBill()");
		 List<BillStatus> billStatus = billingService.getBillStatus(facilityId, buildingId , meterTypeId);
		 
		 return ResponseEntity.ok(billStatus);
	}
	
	@GetMapping("/{facilityId}/{billNumber}/billnumber")
	@PreAuthorize("hasRole('SUBADMIN')")
	public ResponseEntity<?> getBillInfo(@PathVariable String facilityId, @PathVariable Long billNumber){
		System.out.println("SubAdminController getBillInfo()");
		
		Billinfo billInfo = billingService.getBillInfo(facilityId, billNumber);
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<Billinfo>builder().data(billInfo).massage("Metr reding data").build());
	}
}
