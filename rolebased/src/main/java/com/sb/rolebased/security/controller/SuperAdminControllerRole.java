package com.sb.rolebased.security.controller;

import java.util.*;
import java.util.stream.Collectors;

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

import com.sb.rolebased.facility.Entity.Facility;
import com.sb.rolebased.facility.dtos.FacilityDto;
import com.sb.rolebased.facility.repository.FacilityRepository;
import com.sb.rolebased.security.dto.FacilityDTO;
import com.sb.rolebased.security.dto.FacilityUserNameDto;
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

		System.out.println("username "+signupReqWithFacility.getUsername());
		System.out.println("emil "+signupReqWithFacility.getEmail());
		System.out.println("pass "+signupReqWithFacility.getPassword());
		
		System.out.println("role "+signupReqWithFacility.getRole().toString());
		
		if(signupReqWithFacility.getRole().contains("ROLE_SUBADMIN")) {  // contains() method becouse we have to check in list items
			
			System.out.println("SuperAdminControllerRole 2");
			UserRole user = new UserRole(signupReqWithFacility.getUsername(), signupReqWithFacility.getEmail(),
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
	public ResponseEntity<SuccessResponse<List<FacilityDTO>>> facilityListWithoutAdmin(){
		
		System.out.println("SuperAdminControllerRole 6");
		List<FacilityDTO> unAssignedFacilityList = facilityBySuperAdminService.getUnAssignedFacilityList();
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(SuccessResponse.<List<FacilityDTO>>builder().data(unAssignedFacilityList).massage("List Of FacilityName").build());
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

}
