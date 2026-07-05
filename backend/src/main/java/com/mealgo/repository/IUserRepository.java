package com.mealgo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mealgo.entity.User;

public interface IUserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    User getByEmail(String email);

}