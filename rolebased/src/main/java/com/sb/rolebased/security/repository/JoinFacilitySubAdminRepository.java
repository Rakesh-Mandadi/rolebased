package com.sb.rolebased.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sb.rolebased.security.dto.JoinFacilitySubAdmin;

@Repository
public interface JoinFacilitySubAdminRepository extends JpaRepository<JoinFacilitySubAdmin, String> {

}
