package com.mealgo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealgo.entity.OrderItem;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
