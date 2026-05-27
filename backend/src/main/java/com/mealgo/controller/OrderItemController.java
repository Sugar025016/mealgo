package com.mealgo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mealgo.dto.request.OrderItemRequest;
import com.mealgo.dto.response.OrderItemResponse;
import com.mealgo.service.IOrderItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final IOrderItemService orderItemService;

    @GetMapping
    public List<OrderItemResponse> findAll() {
        return orderItemService.findAll();
    }

    @GetMapping("/{id}")
    public OrderItemResponse findById(@PathVariable Integer id) {
        return orderItemService.findById(id);
    }

    @GetMapping("/order/{orderId}")
    public List<OrderItemResponse> findByOrderId(@PathVariable Integer orderId) {
        return orderItemService.findByOrderId(orderId);
    }

    @PostMapping
    public OrderItemResponse create(@Valid @RequestBody OrderItemRequest request) {
        return orderItemService.create(request);
    }

    @PutMapping("/{id}")
    public OrderItemResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody OrderItemRequest request) {

        return orderItemService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        orderItemService.delete(id);
    }
}