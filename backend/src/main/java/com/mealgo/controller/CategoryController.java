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
import com.mealgo.dto.request.CategoryRequest;
import com.mealgo.dto.response.CategoryResponse;
import com.mealgo.service.ICategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> findAll() {

        List<CategoryResponse> categories =
                categoryService.findAll();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        categories
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> findById(
            @PathVariable Integer id) {

        CategoryResponse category =
                categoryService.findById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "查詢成功",
                        category
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(
            @Valid @RequestBody CategoryRequest request) {

        CategoryResponse category =
                categoryService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "新增成功",
                                category
                        )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody CategoryRequest request) {

        CategoryResponse category =
                categoryService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "修改成功",
                        category
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Integer id) {

        categoryService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success("刪除成功")
        );
    }
}