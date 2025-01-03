package com.sb.rolebased.security.controller;

import java.util.*;
//import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.management.relation.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.rolebased.facility.Entity.Building;
//import com.google.gson.Gson;
import com.sb.rolebased.facility.Entity.Facility;
import com.sb.rolebased.facility.Entity.Flat;
import com.sb.rolebased.facility.Entity.Floor;
import com.sb.rolebased.facility.dtos.BuildingMeterDto;
//import com.sb.rolebased.facility.dtos.BuildingMeterDto;
import com.sb.rolebased.facility.dtos.FacilityDto;
import com.sb.rolebased.facility.dtos.FacilityMeterDto;
//import com.sb.rolebased.facility.dtos.FlatsResidentDto;
//import com.sb.rolebased.facility.dtos.ResidentDto;
import com.sb.rolebased.facility.repository.FacilityRepository;
import com.sb.rolebased.facility.repository.FlatRepository;
import com.sb.rolebased.meter.entity.Meter;
import com.sb.rolebased.meter.repository.MeterRepository;
import com.sb.rolebased.security.dto.FacilityDTO;
import com.sb.rolebased.security.dto.FacilityUserNameDto;
import com.sb.rolebased.security.dto.FlatService;
import com.sb.rolebased.security.dto.JoinFacilitySubAdmin;
import com.sb.rolebased.security.dto.MessageResponseRole;
import com.sb.rolebased.security.dto.SignupReqWithFacility;
import com.sb.rolebased.security.dto.SuperAdNumData;
import com.sb.rolebased.security.repository.JoinFacilitySubAdminRepository;
import com.sb.rolebased.security.response.SuccessResponse;
import com.sb.rolebased.security.services.FacilityBySuperAdminService;
import com.sb.rolebased.security.utils.JwtTokenService;
import com.sb.rolebased.usermanagment.defination.RoleTypeRole;
import com.sb.rolebased.usermanagment.entity.RoleRole;
import com.sb.rolebased.usermanagment.entity.UserRole;
import com.sb.rolebased.usermanagment.repository.RoleRepositoryRole;
import com.sb.rolebased.usermanagment.repository.UserRepositoryRole;

import com.sb.rolebased.facility.dtos.FlatDto;
//import com.sb.rolebased.facility.dtos.SubAdminFlashcardDto;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/users")
@RestControllerAdvice
public class SuperAdminControllerRole {
	
	@Autowired
	PasswordEncoder encoder; 
	
	@Autowired
	UserRepositoryRole userRepositoryRole;
	
	@Autowired
	RoleRepositoryRole roleRepositoryRole;
	
	@Autowired
	FacilityBySuperAdminService facilityBySuperAdminService;
	
	  @Autowired
	  FacilityRepository facilityRepository;

	    @Autowired
	 JoinFacilitySubAdminRepository joinFacilitySubAdminRepository;

	    @Autowired
	  UserRepositoryRole userRoleRepository;
	    
	    @Autowired
	    private JwtTokenService jwtTokenService;

	    
	    @Autowired
	    private FlatRepository flatRepository;
	    
	    @Autowired
	    private FlatService flatservice;
	    @Autowired
	    private MeterRepository meterRepository;
	    
//	    @Autowired
//	    private UserRole userRole;
	   
//	    @Autowired
//	    private FlatRepository flatRepository;								
	    
	
	
	@PostMapping("/creatfacilitybysuperadmin")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> creatFacility(@RequestBody FacilityDto facilityDto){
		
		
		System.out.println("SuperAdminControllerRole 2");
		Facility addFacility = facilityBySuperAdminService.addFacility(facilityDto);
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<Facility>builder().data(addFacility).massage("facility created succsessfully").build());
	}
		
	@PostMapping("/creatsubadmin/signup")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupReqWithFacility signupReqWithFacility) {
		System.out.println("SuperAdminControllerRole 1");

		if (userRepositoryRole.existsByName(signupReqWithFacility.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponseRole("Error : username is alredy taken"));
		}
		if (userRepositoryRole.existsByEmail(signupReqWithFacility.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponseRole("Error : Email is alredy taken"));
		}
		if (userRepositoryRole.existsByContact(signupReqWithFacility.getContact())) {
			return ResponseEntity.badRequest().body(new MessageResponseRole("Error : Contact Number is alredy taken"));
		}

		System.out.println("username "+signupReqWithFacility.getUsername());
		System.out.println("email "+signupReqWithFacility.getEmail());
		System.out.println("contact"+signupReqWithFacility.getContact());
		System.out.println("pass "+signupReqWithFacility.getPassword());
	    System.out.println("role "+signupReqWithFacility.getRole().toString());
		
		if(signupReqWithFacility.getRole().contains("ROLE_SUBADMIN")) {  // contains() method because we have to check in list items
			
			System.out.println("SuperAdminControllerRole 2");
			UserRole user = new UserRole(signupReqWithFacility.getUsername(), signupReqWithFacility.getEmail(),signupReqWithFacility.getContact(),
					encoder.encode(signupReqWithFacility.getPassword()));

			Set<String> strRole = signupReqWithFacility.getRole();

			Set<RoleRole> roles = new HashSet<>();

				strRole.forEach(role -> {
					switch (role) {
					case "ROLE_SUBADMIN":
						RoleRole subAdminRole = roleRepositoryRole.findByName(RoleTypeRole.ROLE_SUBADMIN)
								.orElseThrow(() -> new RuntimeException("Erro : RoleNot Found"));
						roles.add(subAdminRole);
						System.out.println("SuperAdminControllerRole 3");
						break;				
					}
				});
			
			user.setRoles(roles);
			UserRole save = userRepositoryRole.save(user);
			
			System.out.println("SuperAdminControllerRole 4");
			System.out.println(save.getName());
			JoinFacilitySubAdmin joinFacilitySubAdmin = new JoinFacilitySubAdmin(signupReqWithFacility.getFacilityId(), signupReqWithFacility.getFacilityName() , save.getId(), save.getName());
				
			JoinFacilitySubAdmin linkFacilitySubAdmin = facilityBySuperAdminService.linkFacilitySubAdmin(joinFacilitySubAdmin);

			System.out.println(linkFacilitySubAdmin.getFacilityName());
			System.out.println("SuperAdminControllerRole 5");
			return ResponseEntity.ok(new MessageResponseRole("user SubAdmin created sucsessfully"));
		}else {
			System.out.println("SuperAdminControllerRole 4");
			return ResponseEntity.badRequest().body(new MessageResponseRole("Error : Not Eligible for SUBADMIN"));
		}		
	}
	

	@GetMapping("/getListOfNewFacility")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<SuccessResponse<List<FacilityDto>>> facilityListWithoutAdmin(){
		
		System.out.println("SuperAdminControllerRole 6");
		List<FacilityDto> unAssignedFacilityList = facilityBySuperAdminService.getUnAssignedFacilityList();
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<List<FacilityDto>>builder().data(unAssignedFacilityList).massage("List Of FacilityName").build());
	}
	
	@GetMapping("/getListFacilityUserName")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public List<FacilityUserNameDto> getFacilityUserNameDtos() {
	    System.out.println("SuperAdminControllerRole getFacilityUserNameDtos()");

	    // Fetch all necessary data in one go
	    List<FacilityDTO> facilities = facilityRepository.findFacility();
	    
	    System.out.println("SuperAdminControllerRole getFacilityUserNameDtos() 2");
	    List<JoinFacilitySubAdmin> joinFacilitySubAdmins = joinFacilitySubAdminRepository.findAll();
	    
	    System.out.println("SuperAdminControllerRole getFacilityUserNameDtos() 3");
	    List<UserRole> userRoles = userRoleRepository.findAll();

	    // Create Maps for quick lookup
	    Map<String, JoinFacilitySubAdmin> facilityToSubAdminMap = joinFacilitySubAdmins.stream()
	        .collect(Collectors.toMap(JoinFacilitySubAdmin::getFacilityId, joinFacilitySubAdmin -> joinFacilitySubAdmin));

	    Map<Long, UserRole> subAdminToUserRoleMap = userRoles.stream()
	        .collect(Collectors.toMap(UserRole::getId, userRole -> userRole));

	    // Generate the list of FacilityUserNameDto
	    List<FacilityUserNameDto> dtoList = facilities.stream()
	        .map(facility -> {
	            JoinFacilitySubAdmin joinedUser = facilityToSubAdminMap.get(facility.getFacilityId());
	            if (joinedUser != null) {
	                UserRole userDetails = subAdminToUserRoleMap.get(joinedUser.getSubAdminId());
	                if (userDetails != null) {
	                    return FacilityUserNameDto.builder()
	                        .facilityId(facility.getFacilityId())
	                        .facilityName(facility.getFacilityName())
	                        .city(facility.getCity())
	                        .userId(userDetails.getId())
	                        .userName(userDetails.getName())
	                        .email(userDetails.getEmail())
	                        .build();
	                }
	            }
	            return FacilityUserNameDto.builder()
	                .facilityId(facility.getFacilityId())
	                .facilityName(facility.getFacilityName())
	                .city(facility.getCity())
	                .userId(null)
	                .userName(null)
	                .email(null)
	                .build();
	        })
	        .collect(Collectors.toList());

	    System.out.println("SuperAdminControllerRole getFacilityUserNameDtos() last");
	    return dtoList;
	}
	


	
	
	@GetMapping("/getNumericData")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<SuccessResponse<SuperAdNumData>> getNumericData() {
		
		System.out.println("SuperAdminControllerRole 5");
		SuperAdNumData numericData = facilityBySuperAdminService.getNumericData();
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<SuperAdNumData>builder().data(numericData).massage("List Of Card Data").build());
	}
	
	
	@DeleteMapping("/{facilityId}")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteFacility(@PathVariable String facilityId){
		
		String removeFacility = facilityBySuperAdminService.removeFacility(facilityId);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<String>builder().data(removeFacility).massage("Facility is deletd with Id"+ facilityId).build());
	}
	
	@PreAuthorize("hasAnyRole('SUPERADMIN','SUBADMIN')")
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String tokenHeader) {
		System.out.println("logout - "+ tokenHeader);
	    if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing or invalid Authorization header");
	    }
	    String token = tokenHeader.substring(7);
	    jwtTokenService.invalidateToken(token);
	    return ResponseEntity.ok("Logout successful");
	}
	
//    @GetMapping("/getSubAdminFlashcards")
//    @PreAuthorize("hasRole('SUPERADMIN')")
//    public List<SubAdminFlashcardDto> getSubAdminFlashcards() {
//        System.out.println("Fetching SubAdmin Flashcards");
//
//        // Fetch sub-admin users
//        List<UserRole> subAdmins = userRepositoryRole.findAllSubAdmins();
//
//        // Convert to DTOs
//        List<SubAdminFlashcardDto> flashcards = subAdmins.stream()
//            .map(userRole -> SubAdminFlashcardDto.builder()
//                .userId(userRole.getId())
//                .username(userRole.getName())
//                .email(userRole.getEmail())
//                .contactNumber(userRole.getContact())
//                .build())
//            .collect(Collectors.toList());
//
//        System.out.println("SubAdmin Flashcards fetched successfully");
//        return flashcards;
//    }





	
//    @GetMapping("/{id}")
//    public ResponseEntity<SubAdmin> getSubAdminById(@PathVariable Long id) {
//        return subAdminService.getSubAdminById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
// userName, contact  ,email
	@GetMapping("/getSubAdmins")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public String getSubAdmins() {
	    List<UserRole> users = userRepositoryRole.findAllSubAdmins(RoleTypeRole.ROLE_SUBADMIN);
	    
	    if (users == null || users.isEmpty()) {
	        return "[]"; 
	    }
	    
	    List<Map<String, String>> usersList = new ArrayList<>();
	    for (UserRole userRole : users) {
	        Map<String, String> userMap = new HashMap<>();
	        userMap.put("Name", userRole.getName());
	        userMap.put("Contact", userRole.getContact());
	        userMap.put("Email", userRole.getEmail());
	        usersList.add(userMap);
	    }
	    
	    ObjectMapper mapper = new ObjectMapper();
	    try {
	        return mapper.writeValueAsString(usersList); 
	    } catch (JsonProcessingException e) {
	        e.printStackTrace(); 
	        return "[]"; 
	    }

	}
    private static final Logger logger = LoggerFactory.getLogger(SuperAdminControllerRole.class);

	
    @GetMapping("/getFlatDetails")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public List<FlatDto> getFlatDetails() {
        logger.info("Fetching all meters...");

        List<Meter> flats = meterRepository.findAll();
        logger.info("Retrieved {} meter records.", flats.size());

        Map<Long, Integer> flatCountMap = new HashMap<>();

        for (Meter meter : flats) {
           try {
        	   Long flatId = meter.getFlat().getFlatNumber();
               flatCountMap.put(flatId, flatCountMap.getOrDefault(flatId, 0) + 1);
           }
           catch (Exception e) {
        	   
		}
        }
        List<FlatDto> flatDetails = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : flatCountMap.entrySet()) {
            FlatDto flatDto = new FlatDto(entry.getKey(), entry.getValue(), null);  // Create FlatDto object with flatId and meter count
            flatDetails.add(flatDto);  // Add FlatDto to the list
        }

        logger.info("Flat meter count map: {}", flatDetails);
        return flatDetails;
    }
//    @GetMapping("/getFlatDetails")
//    @PreAuthorize("hasRole('SUPERADMIN')")
//    public List<FlatDto> getFlatDetails() {
//        logger.info("Fetching all flats and meters...");
//
//        // Retrieve all flats
//        List<Flat> allFlats = flatRepository.findAll();
//        logger.info("Retrieved {} flat records.", allFlats.size());
//
//        // Retrieve all meters
//        List<Meter> allMeters = meterRepository.findAll();
//        logger.info("Retrieved {} meter records.", allMeters.size());
//
//        // Map to store meter count for each flat
//        Map<Long, Integer> flatCountMap = new HashMap<>();
//
//        // Initialize all flats with 0 meters
//        for (Flat flat : allFlats) {
//            flatCountMap.put(flat.getFlatNumber(), 0);
//        }
//
//        // Count meters for each flat
//        for (Meter meter : allMeters) {
//            try {
//                Long flatId = meter.getFlat().getFlatNumber();
//                flatCountMap.put(flatId, flatCountMap.getOrDefault(flatId, 0) + 1);
//            } catch (Exception e) {
//                logger.error("Error processing meter for flat: {}", meter.getFlat(), e);
//            }
//        }
//
//        // Create FlatDto list
//        List<FlatDto> flatDetails = new ArrayList<>();
//        for (Map.Entry<Long, Integer> entry : flatCountMap.entrySet()) {
//            FlatDto flatDto = new FlatDto(entry.getKey(), entry.getValue(), null);  // flatId, meter count, additional data (null for now)
//            flatDetails.add(flatDto);
//        }
//
//        logger.info("Final flat meter count map: {}", flatDetails);
//        return flatDetails;
//    }

    
   
        @GetMapping("/getFacilityMeterDetails")
        @PreAuthorize("hasRole('SUPERADMIN')")
        public List<FacilityMeterDto> getFacilityMeterDetails() {
            logger.info("Fetching meter details for all facilities...");

            List<Facility> facilities = facilityRepository.findAll();
            logger.info("Retrieved {} facilities from database.", facilities.size());

            List<FacilityMeterDto> facilityDetails = new ArrayList<>();

            for (Facility facility : facilities) {
                int meterCount = facility.getMeters().size();
                FacilityMeterDto facilityDto = new FacilityMeterDto(facility.getFacilityId(),facility.getFacilityName(), meterCount);
                facilityDetails.add(facilityDto);
            }

            logger.info("Facility meter count details: {}", facilityDetails);
            return facilityDetails;
        }

        //blockname , flor no , flatno ,username 
        @GetMapping("/{facilityId}/getAssignedMeterDetails")
//        @PreAuthorize("hasRole('SUPERADMIN')")
        public List<BuildingMeterDto> getAssignedMeterDetails(@PathVariable String facilityId) {
            // Validate facilityId early
            if (facilityId == null || facilityId.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Facility ID cannot be null or empty");
            }

            Optional<Facility> facility = facilityRepository.findById(facilityId);

            // Handle facility not found - Return 404
            if (facility.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No facility found with ID: " + facilityId);
            }

            List<Building> buildings = facility.get().getBuilding();

            if (buildings == null || buildings.isEmpty()) {
                // Log and return an empty list instead of throwing an error
                System.out.println("No buildings found for facility ID: " + facilityId);
                return List.of();
            }

            List<BuildingMeterDto> result = new ArrayList<>();

            for (Building building : buildings) {
                for (Floor floor : building.getFloor()) {
                    for (Flat flat : floor.getFlats()) {
                    	System.out.println(flat.getMeter());
                    	if (!flat.getMeter().isEmpty())
                        result.add(new BuildingMeterDto(
                            building.getBuildingName(),
                            floor.getFloorNumber(),
                            flat.getFlatNumber(),
                            null  // Null value, consider adding user or meter data if available
                        ));
                    }
                }
            }

            return result;
        }



    
        
}


