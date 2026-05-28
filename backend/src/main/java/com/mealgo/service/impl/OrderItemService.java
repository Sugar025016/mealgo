package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.dto.request.OrderItemRequest;
import com.mealgo.dto.response.OrderItemResponse;
import com.mealgo.entity.OrderItem;
import com.mealgo.exception.ResourceNotFoundException;
import com.mealgo.repository.IOrderItemRepository;
import com.mealgo.service.IOrderItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemService implements IOrderItemService {

    private final IOrderItemRepository orderItemRepository;

    @Override
    public List<OrderItemResponse> findAll() {
        return orderItemRepository.findAll()
                .stream()
                .map(OrderItemResponse::new)
                .toList();
    }

    @Override
    public List<OrderItemResponse> findByOrderId(Integer orderId) {
        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(OrderItemResponse::new)
                .toList();
    }

    @Override
    public OrderItemResponse findById(Integer id) {

        OrderItem orderItem = getOrderItem(id);

        return new OrderItemResponse(orderItem);
    }

    @Override
    public void delete(Integer id) {

        OrderItem orderItem = getOrderItem(id);

        orderItemRepository.delete(orderItem);
    }

    private OrderItem getOrderItem(Integer id) {

        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("訂單明細"));
    }

}