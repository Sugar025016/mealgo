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
import com.mealgo.dto.request.CategoryRequest;
import com.mealgo.dto.response.CategoryResponse;
import com.mealgo.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Category", description = "類別 API")
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @Operation(summary = "查詢全部類別")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> findAll() {

        List<CategoryResponse> categories = categoryService.findAll();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        categories));
    }

    @Operation(summary = "查詢單一類別")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> findById(
            @PathVariable Integer id) {

        CategoryResponse category = categoryService.findById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        category));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "新增類別")
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(
            @Valid @RequestBody CategoryRequest request) {

        CategoryResponse category = categoryService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "新增成功",
                                category));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "修改類別")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody CategoryRequest request) {

        CategoryResponse category = categoryService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "修改成功",
                        category));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "刪除類別")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Integer id) {

        categoryService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success("刪除成功"));
    }
}