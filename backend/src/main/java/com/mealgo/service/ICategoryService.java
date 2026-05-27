package com.mealgo.service;

import java.util.List;

import com.mealgo.dto.request.CategoryRequest;
import com.mealgo.dto.response.CategoryResponse;

public interface ICategoryService {

    List<CategoryResponse> findAll();

    CategoryResponse findById(Integer id);

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(Integer id, CategoryRequest request);

    void delete(Integer id);
}