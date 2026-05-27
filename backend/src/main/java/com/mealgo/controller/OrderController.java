package com.mealgo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mealgo.dto.request.OrderRequest;
import com.mealgo.dto.response.OrderResponse;
import com.mealgo.service.IOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @GetMapping
    public List<OrderResponse> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Integer id) {
        return orderService.findById(id);
    }

    @GetMapping("/number/{orderNumber}")
    public OrderResponse findByOrderNumber(@PathVariable String orderNumber) {
        return orderService.findByOrderNumber(orderNumber);
    }

    @GetMapping("/user/{userId}")
    public List<OrderResponse> findByUserId(@PathVariable Integer userId) {
        return orderService.findByUserId(userId);
    }

    @GetMapping("/shop/{shopId}")
    public List<OrderResponse> findByShopId(@PathVariable Integer shopId) {
        return orderService.findByShopId(shopId);
    }

    @PostMapping
    public OrderResponse create(@Valid @RequestBody OrderRequest request) {
        return orderService.create(request);
    }

    @PutMapping("/{id}")
    public OrderResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody OrderRequest request) {

        return orderService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        orderService.delete(id);
    }
}