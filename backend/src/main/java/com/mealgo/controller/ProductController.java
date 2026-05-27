package com.mealgo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mealgo.dto.ApiResponse;
import com.mealgo.dto.request.ProductRequest;
import com.mealgo.dto.response.ProductResponse;
import com.mealgo.service.IProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> findAll() {

        List<ProductResponse> products = productService.findAll();

        return ResponseEntity.ok(
                ApiResponse.success("查詢成功", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> findById(
            @PathVariable Integer id) {

        ProductResponse product = productService.findById(id);

        return ResponseEntity.ok(
                ApiResponse.success("查詢成功", product));
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> findByShopId(
            @PathVariable Integer shopId) {

        List<ProductResponse> products = productService.findByShopId(shopId);

        return ResponseEntity.ok(
                ApiResponse.success("查詢成功", products));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> create(
            @Valid @RequestBody ProductRequest request) {

        ProductResponse product = productService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "新增成功",
                                product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ProductRequest request) {

        ProductResponse product = productService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "修改成功",
                        product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Integer id) {

        productService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success("刪除成功"));
    }
}