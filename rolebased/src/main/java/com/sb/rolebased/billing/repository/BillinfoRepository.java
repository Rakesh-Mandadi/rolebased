package com.sb.rolebased.billing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.rolebased.billing.entity.Billinfo;

public interface BillinfoRepository extends JpaRepository<Billinfo, Long> {

	List<Billinfo> findByBillNumberIn(List<Long> billNList);

	Billinfo findByBillNumber(Long billNumber);

}
