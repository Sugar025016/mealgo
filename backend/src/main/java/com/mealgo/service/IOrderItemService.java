package com.mealgo.service;

import java.util.List;

import com.mealgo.dto.request.OrderItemRequest;
import com.mealgo.dto.response.OrderItemResponse;

public interface IOrderItemService {

    List<OrderItemResponse> findAll();

    List<OrderItemResponse> findByOrderId(Integer orderId);

    OrderItemResponse findById(Integer id);

    OrderItemResponse create(OrderItemRequest request);

    OrderItemResponse update(Integer id, OrderItemRequest request);

    void delete(Integer id);
}