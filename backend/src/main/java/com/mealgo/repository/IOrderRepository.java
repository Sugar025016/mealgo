package com.mealgo.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mealgo.entity.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {

}
