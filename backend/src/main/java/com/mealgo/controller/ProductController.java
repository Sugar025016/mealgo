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
import com.mealgo.dto.request.ProductRequest;
import com.mealgo.dto.response.ProductResponse;
import com.mealgo.service.IProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Product", description = "產品 API")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @Operation(summary = "查詢全部產品")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> findAll() {

        List<ProductResponse> products = productService.findAll();

        return ResponseEntity.ok(
                ApiResponse.success("查詢成功", products));
    }

    @Operation(summary = "查詢單一產品")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> findById(
            @PathVariable Integer id) {

        ProductResponse product = productService.findById(id);

        return ResponseEntity.ok(
                ApiResponse.success("查詢成功", product));
    }

    @Operation(summary = "根據商店 ID 查詢產品")
    @GetMapping("/shop/{shopId}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> findByShopId(
            @PathVariable Integer shopId) {

        List<ProductResponse> products = productService.findByShopId(shopId);

        return ResponseEntity.ok(
                ApiResponse.success("查詢成功", products));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "新增產品")
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

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "修改產品")
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

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "刪除產品")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Integer id) {

        productService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success("刪除成功"));
    }
}