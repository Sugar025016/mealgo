package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.dto.request.CategoryRequest;
import com.mealgo.dto.response.CategoryResponse;
import com.mealgo.entity.Category;
import com.mealgo.repository.ICategoryRepository;
import com.mealgo.service.ICategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::new)
                .toList();
    }

    @Override
    public CategoryResponse findById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return new CategoryResponse(category);
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {

        Category category = new Category();

        category.setName(request.getName());

        return new CategoryResponse(
                categoryRepository.save(category));
    }

    @Override
    public CategoryResponse update(Integer id,
            CategoryRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(request.getName());

        return new CategoryResponse(
                categoryRepository.save(category));
    }

    @Override
    public void delete(Integer id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);
    }
}