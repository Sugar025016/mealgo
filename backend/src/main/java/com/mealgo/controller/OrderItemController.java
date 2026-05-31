package com.mealgo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealgo.dto.ApiResponse;
import com.mealgo.dto.response.OrderItemResponse;
import com.mealgo.service.IOrderItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "OrderItem", description = "訂單項目 API")
@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final IOrderItemService orderItemService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "查詢全部訂單項目")
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderItemResponse>>> findAll() {

        List<OrderItemResponse> orderItems = orderItemService.findAll();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        orderItems));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "查詢單一訂單項目")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderItemResponse>> findById(
            @PathVariable Integer id) {

        OrderItemResponse orderItem = orderItemService.findById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        orderItem));
    }

    @Operation(summary = "根據訂單 ID 查詢訂單項目")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<List<OrderItemResponse>>> findByOrderId(
            @PathVariable Integer orderId) {

        List<OrderItemResponse> orderItems = orderItemService.findByOrderId(orderId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        orderItems));
    }

    // @Operation(summary = "刪除訂單項目")
    // @DeleteMapping("/{id}")
    // public ResponseEntity<ApiResponse<Void>> delete(
    // @PathVariable Integer id) {

    // orderItemService.delete(id);

    // return ResponseEntity.ok(
    // ApiResponse.success("刪除成功"));
    // }
}