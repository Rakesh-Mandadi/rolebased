package com.sb.rolebased.newforgotpassword;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lombok.Getter;

import java.util.Optional;


@Repository

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    Optional<OtpEntity> findByRecipient(String recipient);
    Optional<OtpEntity> deleteByRecipient(String recipient);
}
