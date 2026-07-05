package com.mealgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mealgo.entity.OrderItem;

public interface IOrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrderId(Integer orderId);
}
