package com.mealgo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealgo.entity.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {
    // Optional<Order> findByOrderNumber(String orderNumber);

    Optional<Order> findByUserIdAndId(Integer userId, Integer id);

    Optional<Order> findByUserIdAndOrderNumber(Integer userId, String orderNumber);

    List<Order> findByUserIdAndShopId(Integer userId, Integer shopId);

    List<Order> findByUserId(Integer userId);

    List<Order> findByShopId(Integer shopId);
}
