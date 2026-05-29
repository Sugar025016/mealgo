package com.mealgo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealgo.dto.ApiResponse;
import com.mealgo.dto.request.OrderRequest;
import com.mealgo.dto.request.OrderNoteRequest;
import com.mealgo.dto.response.OrderResponse;
import com.mealgo.security.CustomUserDetails;
import com.mealgo.service.IOrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Order", description = "訂單 API")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    // @Operation(summary = "查詢全部訂單")
    // @GetMapping
    // public ResponseEntity<ApiResponse<List<OrderResponse>>> findAll(
    // @AuthenticationPrincipal CustomUserDetails userDetails) {

    // List<OrderResponse> orders = orderService.findAll();

    // return ResponseEntity.ok(
    // ApiResponse.success(
    // "查詢成功",
    // orders));
    // }

    @Operation(summary = "查詢單一訂單")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> findById(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer id) {

        OrderResponse order = orderService.findByUserIdAndId(userDetails.getId(), id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        order));
    }

    @Operation(summary = "根據訂單編號查詢訂單")
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<ApiResponse<OrderResponse>> findByOrderNumber(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String orderNumber) {

        OrderResponse order = orderService.findByUserIdAndOrderNumber(userDetails.getId(), orderNumber);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        order));
    }

    @Operation(summary = "根據使用者 ID 查詢訂單")
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> findByUserId(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<OrderResponse> orders = orderService.findByUserId(userDetails.getId());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        orders));
    }

    @Operation(summary = "根據商店 ID 查詢訂單")
    @GetMapping("/shop/{shopId}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> findByShopId(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer shopId) {

        List<OrderResponse> orders = orderService.findByShopId(userDetails.getId(), shopId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        orders));
    }

    @Operation(summary = "新增訂單")
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> create(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody OrderRequest request) {

        OrderResponse order = orderService.create(userDetails.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "新增成功",
                                order));
    }

    @Operation(summary = "修改自己的訂單備註")
    @PatchMapping("/{id}/note")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderNote(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer id,
            @Valid @RequestBody OrderNoteRequest request) {

        OrderResponse order = orderService.updateOrderNote(
                userDetails.getId(),
                id,
                request);

        return ResponseEntity.ok(
                ApiResponse.success("修改成功", order));
    }

    @Operation(summary = "取消自己的訂單")
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancel(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer id) {

        OrderResponse order = orderService.cancel(userDetails.getId(), id);

        return ResponseEntity.ok(
                ApiResponse.success("取消成功", order));
    }

    // @Operation(summary = "修改訂單")
    // @PutMapping("/{id}")
    // public ResponseEntity<ApiResponse<OrderResponse>> update(
    // @AuthenticationPrincipal CustomUserDetails userDetails,
    // @PathVariable Integer id,
    // @Valid @RequestBody OrderNoteRequest request) {

    // OrderResponse order = orderService.update(userDetails.getId(), request);

    // return ResponseEntity.ok(
    // ApiResponse.success(
    // "修改成功",
    // order));
    // }

    // @Operation(summary = "修改訂單狀態")
    // @PutMapping("/{id}/{statusCode}")
    // public ResponseEntity<ApiResponse<OrderResponse>> update(
    // @PathVariable Integer id,
    // @PathVariable Integer StatusCode) {

    // OrderResponse order = orderService.updateStatus(id, StatusCode);

    // return ResponseEntity.ok(
    // ApiResponse.success(
    // "狀態修改成功",
    // order));
    // }

    // @Operation(summary = "刪除訂單")
    // @DeleteMapping("/{id}")
    // public ResponseEntity<ApiResponse<Void>> delete(
    // @PathVariable Integer id) {

    // orderService.delete(id);

    // return ResponseEntity.ok(
    // ApiResponse.success("刪除成功"));
    // }
}