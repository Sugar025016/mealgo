package com.mealgo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

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
    public List<CategoryResponse> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(
            @PathVariable Integer id) {

        return categoryService.findById(id);
    }

    @PostMapping
    public CategoryResponse create(
            @Valid @RequestBody CategoryRequest request) {

        return categoryService.create(request);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody CategoryRequest request) {

        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Integer id) {

        categoryService.delete(id);
    }
}