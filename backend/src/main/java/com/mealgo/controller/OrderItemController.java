package com.mealgo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealgo.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<OrderItemResponse>>> findAll() {

        List<OrderItemResponse> orderItems = orderItemService.findAll();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        orderItems));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderItemResponse>> findById(
            @PathVariable Integer id) {

        OrderItemResponse orderItem = orderItemService.findById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        orderItem));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<List<OrderItemResponse>>> findByOrderId(
            @PathVariable Integer orderId) {

        List<OrderItemResponse> orderItems = orderItemService.findByOrderId(orderId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        orderItems));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderItemResponse>> create(
            @Valid @RequestBody OrderItemRequest request) {

        OrderItemResponse orderItem = orderItemService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "新增成功",
                                orderItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderItemResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody OrderItemRequest request) {

        OrderItemResponse orderItem = orderItemService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "修改成功",
                        orderItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Integer id) {

        orderItemService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success("刪除成功"));
    }
}