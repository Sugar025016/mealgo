package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.dto.request.OrderItemRequest;
import com.mealgo.dto.response.OrderItemResponse;
import com.mealgo.entity.Order;
import com.mealgo.entity.OrderItem;
import com.mealgo.entity.Product;
import com.mealgo.repository.IOrderItemRepository;
import com.mealgo.repository.IOrderRepository;
import com.mealgo.repository.IProductRepository;
import com.mealgo.service.IOrderItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemService implements IOrderItemService {

    private final IOrderItemRepository orderItemRepository;
    private final IOrderRepository orderRepository;
    private final IProductRepository productRepository;

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
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        return new OrderItemResponse(orderItem);
    }

    @Override
    public OrderItemResponse create(OrderItemRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderItem orderItem = new OrderItem();

        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQty(request.getQty());

        int price = request.getPrice() > 0
                ? request.getPrice()
                : product.getPrice();

        orderItem.setPrice(price);
        orderItem.setCustomerNote(request.getCustomerNote());

        return new OrderItemResponse(orderItemRepository.save(orderItem));
    }

    @Override
    public OrderItemResponse update(Integer id, OrderItemRequest request) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQty(request.getQty());

        int price = request.getPrice() > 0
                ? request.getPrice()
                : product.getPrice();

        orderItem.setPrice(price);
        orderItem.setCustomerNote(request.getCustomerNote());

        return new OrderItemResponse(orderItemRepository.save(orderItem));
    }

    @Override
    public void delete(Integer id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        orderItemRepository.delete(orderItem);
    }
}