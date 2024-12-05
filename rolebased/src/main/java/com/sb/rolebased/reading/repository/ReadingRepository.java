package com.sb.rolebased.reading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sb.rolebased.reading.entity.DcuRead;

@Repository
public interface ReadingRepository extends JpaRepository<DcuRead, Long> {

	DcuRead findByMacAdd(String macAdd);
	
	List<DcuRead> findAllByMacAddIn(List<String> macAddresses);

}
