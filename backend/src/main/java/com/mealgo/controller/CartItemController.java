package com.mealgo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealgo.dto.ApiResponse;
import com.mealgo.dto.request.CartItemRequest;
import com.mealgo.dto.response.CartItemResponse;
import com.mealgo.security.CustomUserDetails;
import com.mealgo.service.ICartItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "CartItem", description = "購物車商品 API")
@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final ICartItemService cartItemService;

    @Operation(summary = "修改購物車商品")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CartItemResponse>> update(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer id,
            @Valid @RequestBody CartItemRequest request) {

        CartItemResponse item = cartItemService.update(
                userDetails.getId(),
                id,
                request);

        return ResponseEntity.ok(
                ApiResponse.success("修改成功", item));

    }

    @Operation(summary = "刪除購物車商品")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer id) {

        cartItemService.delete(userDetails.getId(), id);

        return ResponseEntity.ok(
                ApiResponse.success("刪除成功"));
    }
}