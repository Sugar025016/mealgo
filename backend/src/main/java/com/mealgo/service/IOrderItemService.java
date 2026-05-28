package com.mealgo.service;

import java.util.List;

import com.mealgo.dto.response.OrderItemResponse;

public interface IOrderItemService {

    List<OrderItemResponse> findAll();

    List<OrderItemResponse> findByOrderId(Integer orderId);

    OrderItemResponse findById(Integer id);

    void delete(Integer id);
}