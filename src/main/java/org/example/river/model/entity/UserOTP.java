package org.example.river.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.example.river.model.enumeration.OtpStatus;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table
@Getter
@Setter
public class UserOTP {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(6)")
    private String otp;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(STRING)
    private OtpStatus status;

    @Column(nullable = false)
    private LocalDateTime expiresAt;
}
