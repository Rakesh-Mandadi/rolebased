package com.sb.rolebased.usermanagment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sb.rolebased.usermanagment.defination.RoleTypeRole;
import com.sb.rolebased.usermanagment.entity.RoleRole;

@Repository
public interface RoleRepositoryRole extends JpaRepository<RoleRole, Integer> {

	Optional<RoleRole> findByName(RoleTypeRole roleType);

}
