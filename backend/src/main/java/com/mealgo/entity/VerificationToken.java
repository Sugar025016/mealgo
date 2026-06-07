package com.mealgo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.mealgo.enums.VerificationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "verification_tokens")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true, length = 255)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private VerificationType type;

    @Column(name = "expire_at", nullable = false)
    private LocalDateTime expireAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Column(name = "create_time", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createTime;
}
