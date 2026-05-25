package com.mealgo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mealgo.entity.User;

// @Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    boolean existsByAccount(String account);

}