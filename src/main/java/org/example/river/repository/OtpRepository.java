package org.example.river.repository;

import org.example.river.model.entity.UserOTP;
import org.example.river.model.enumeration.OtpStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<UserOTP, Long> {
    @Query("select o from UserOTP o where o.email = :email and o.status = :status and o.expiresAt > :currentTime")
    Optional<UserOTP> findValidOtp(String email, LocalDateTime currentTime, OtpStatus status);
}
