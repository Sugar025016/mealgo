package com.mealgo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealgo.dto.ApiResponse;
import com.mealgo.dto.request.CartRequest;
import com.mealgo.dto.response.CartResponse;
import com.mealgo.security.CustomUserDetails;
import com.mealgo.service.ICartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Cart", description = "購物車 API")
@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    @Operation(summary = "查詢自己的全部購物車")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CartResponse>>> findAll(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<CartResponse> carts = cartService.findAllByUserId(userDetails.getId());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        carts));
    }

    @Operation(summary = "查詢單一購物車")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CartResponse>> findById(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer id) {

        CartResponse cart = cartService.findByUserIdAndId(userDetails.getId(), id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        cart));
    }

    @Operation(summary = "新增購物車")
    @PostMapping
    public ResponseEntity<ApiResponse<CartResponse>> create(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CartRequest request) {

        CartResponse cart = cartService.create(userDetails.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "新增成功",
                                cart));
    }

    @Operation(summary = "刪除購物車")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer id) {

        cartService.delete(userDetails.getId(), id);

        return ResponseEntity.ok(
                ApiResponse.success("刪除成功"));
    }
}