package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.dto.request.OrderItemRequest;
import com.mealgo.dto.response.OrderItemResponse;
import com.mealgo.entity.Order;
import com.mealgo.entity.OrderItem;
import com.mealgo.entity.Product;
import com.mealgo.exception.ResourceNotFoundException;
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

                OrderItem orderItem = getOrderItem(id);

                return new OrderItemResponse(orderItem);
        }

        @Override
        public OrderItemResponse create(OrderItemRequest request) {

                Order order = getOrder(request.getOrderId());
                Product product = getProduct(request.getProductId());

                OrderItem orderItem = new OrderItem();

                orderItem.setOrder(order);
                orderItem.setProduct(product);
                orderItem.setQty(request.getQty());

                int price = request.getPrice() > 0
                                ? request.getPrice()
                                : product.getPrice();

                orderItem.setPrice(price);
                orderItem.setCustomerNote(request.getCustomerNote());

                return new OrderItemResponse(
                                orderItemRepository.save(orderItem));
        }

        @Override
        public OrderItemResponse update(Integer id, OrderItemRequest request) {

                OrderItem orderItem = getOrderItem(id);
                Order order = getOrder(request.getOrderId());
                Product product = getProduct(request.getProductId());

                orderItem.setOrder(order);
                orderItem.setProduct(product);
                orderItem.setQty(request.getQty());

                int price = request.getPrice() > 0
                                ? request.getPrice()
                                : product.getPrice();

                orderItem.setPrice(price);
                orderItem.setCustomerNote(request.getCustomerNote());

                return new OrderItemResponse(
                                orderItemRepository.save(orderItem));
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

        private Order getOrder(Integer id) {

                return orderRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("訂單"));
        }

        private Product getProduct(Integer id) {

                return productRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("商品"));
        }
}