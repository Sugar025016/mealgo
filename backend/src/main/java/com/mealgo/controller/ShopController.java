package com.mealgo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealgo.dto.ApiResponse;
import com.mealgo.dto.request.ShopRequest;
import com.mealgo.dto.response.ShopResponse;
import com.mealgo.service.IShopService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Shop", description = "店家 API")
@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {

    private final IShopService shopService;

    /**
     * 查詢全部店家
     */
    @Operation(summary = "查詢全部店家")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ShopResponse>>> findAll() {

        List<ShopResponse> shops = shopService.findAll();

        return ResponseEntity.ok(ApiResponse.success("查詢成功", shops));
    }

    /**
     * 查詢單一店家
     */
    @Operation(summary = "查詢單一店家")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShopResponse>> findById(
            @PathVariable Integer id) {

        ShopResponse shop = shopService.findById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        shop));

    }

    /**
     * 新增店家
     */
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "新增店家")
    @PostMapping
    public ResponseEntity<ApiResponse<ShopResponse>> create(
            @Valid @RequestBody ShopRequest request) {

        ShopResponse shop = shopService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "新增成功",
                                shop));
    }

    /**
     * 修改店家
     */
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "修改店家")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ShopResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ShopRequest request) {

        ShopResponse shop = shopService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "修改成功",
                        shop));
    }

    /**
     * 刪除店家
     */
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "刪除店家")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Integer id) {

        shopService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "刪除成功",
                        null));
    }

}