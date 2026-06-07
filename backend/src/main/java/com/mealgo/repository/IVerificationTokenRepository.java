package com.mealgo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mealgo.entity.VerificationToken;

public interface IVerificationTokenRepository
        extends JpaRepository<VerificationToken, Integer> {

    Optional<VerificationToken> findByToken(String token);

}
