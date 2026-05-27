package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.dto.request.OrderRequest;
import com.mealgo.dto.response.OrderResponse;
import com.mealgo.entity.Order;
import com.mealgo.entity.Shop;
import com.mealgo.entity.User;
import com.mealgo.enums.OrderStatus;
import com.mealgo.enums.PayMethod;
import com.mealgo.repository.IOrderRepository;
import com.mealgo.repository.IShopRepository;
import com.mealgo.repository.IUserRepository;
import com.mealgo.service.IOrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IUserRepository userRepository;
    private final IShopRepository shopRepository;

    @Override
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::new)
                .toList();
    }

    @Override
    public OrderResponse findById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return new OrderResponse(order);
    }

    @Override
    public OrderResponse findByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return new OrderResponse(order);
    }

    @Override
    public List<OrderResponse> findByUserId(Integer userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderResponse::new)
                .toList();
    }

    @Override
    public List<OrderResponse> findByShopId(Integer shopId) {
        return orderRepository.findByShopId(shopId)
                .stream()
                .map(OrderResponse::new)
                .toList();
    }

    @Override
    public OrderResponse create(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Shop shop = shopRepository.findById(request.getShopId())
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        Order order = new Order();

        order.setOrderNumber(request.getOrderNumber());
        order.setOrderNote(request.getOrderNote());

        order.setDeliveryFee(request.getDeliveryFee());
        order.setSubtotal(request.getSubtotal());
        order.setTotalPrice(request.getTotalPrice());

        order.setStatus(request.getStatus() != null
                ? request.getStatus()
                : OrderStatus.PENDING.getCode());

        order.setPayMethod(request.getPayMethod() != null
                ? request.getPayMethod()
                : PayMethod.CASH.getCode());

        order.setCity(request.getCity());
        order.setArea(request.getArea());
        order.setStreet(request.getStreet());
        order.setDetail(request.getDetail());
        order.setLat(request.getLat());
        order.setLng(request.getLng());

        order.setUser(user);
        order.setShop(shop);

        return new OrderResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse update(Integer id, OrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Shop shop = shopRepository.findById(request.getShopId())
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        order.setOrderNumber(request.getOrderNumber());
        order.setOrderNote(request.getOrderNote());

        order.setDeliveryFee(request.getDeliveryFee());
        order.setSubtotal(request.getSubtotal());
        order.setTotalPrice(request.getTotalPrice());

        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }

        if (request.getPayMethod() != null) {
            order.setPayMethod(request.getPayMethod());
        }

        order.setCity(request.getCity());
        order.setArea(request.getArea());
        order.setStreet(request.getStreet());
        order.setDetail(request.getDetail());
        order.setLat(request.getLat());
        order.setLng(request.getLng());

        order.setUser(user);
        order.setShop(shop);

        return new OrderResponse(orderRepository.save(order));
    }

    @Override
    public void delete(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        orderRepository.delete(order);
    }
}