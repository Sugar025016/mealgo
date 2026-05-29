package com.mealgo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mealgo.entity.User;

// @Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String account);

    Optional<User> findByEmail(String email);

}