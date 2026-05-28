package com.mealgo.service;

import java.util.List;

import com.mealgo.dto.request.OrderRequest;
import com.mealgo.dto.response.OrderResponse;

public interface IOrderService {

    List<OrderResponse> findAll();

    OrderResponse findById(Integer id);

    OrderResponse findByOrderNumber(String orderNumber);

    List<OrderResponse> findByUserId(Integer userId);

    List<OrderResponse> findByShopId(Integer shopId);

    OrderResponse create(OrderRequest request);

    OrderResponse update(Integer id, OrderRequest request);

    OrderResponse updateStatus(Integer id, Integer statusCode);

    void delete(Integer id);
}