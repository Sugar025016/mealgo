package com.mealgo.service;

import java.util.List;

import com.mealgo.dto.request.OrderRequest;
import com.mealgo.dto.request.OrderNoteRequest;
import com.mealgo.dto.response.OrderResponse;

public interface IOrderService {

    List<OrderResponse> findAll();

    OrderResponse findByUserIdAndId(Integer userId, Integer id);

    OrderResponse findByUserIdAndOrderNumber(Integer userId, String orderNumber);

    List<OrderResponse> findByUserId(Integer userId);

    List<OrderResponse> findByShopId(Integer userId, Integer shopId);

    OrderResponse create(Integer userId, OrderRequest request);

    OrderResponse updateOrderNote(Integer userId, Integer id, OrderNoteRequest request);

    // OrderResponse update(Integer id, OrderNoteRequest request);

    OrderResponse updateStatus(Integer id, Integer statusCode);

    OrderResponse cancel(Integer userId, Integer id);

    void delete(Integer id);
}