package com.mealgo.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.mealgo.dto.request.CategoryRequest;
import com.mealgo.dto.response.CategoryResponse;
import com.mealgo.entity.Category;
import com.mealgo.exception.ResourceNotFoundException;
import com.mealgo.repository.ICategoryRepository;
import com.mealgo.service.ICategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    @Override
    @Cacheable(value = "categories", key = "'all'")
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::new)
                .toList();
    }

    @Override
    @Cacheable(value = "category", key = "#id")
    public CategoryResponse findById(Integer id) {

        Category category = getCategory(id);

        return new CategoryResponse(category);
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public CategoryResponse create(CategoryRequest request) {

        Category category = new Category();

        category.setName(request.getName());

        return new CategoryResponse(
                categoryRepository.save(category));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "category", key = "#id"),
            @CacheEvict(value = "categories", allEntries = true)
    })
    public CategoryResponse update(
            Integer id,
            CategoryRequest request) {

        Category category = getCategory(id);

        category.setName(request.getName());

        return new CategoryResponse(
                categoryRepository.save(category));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "category", key = "#id"),
            @CacheEvict(value = "categories", allEntries = true)
    })
    public void delete(Integer id) {

        Category category = getCategory(id);

        categoryRepository.delete(category);
    }

    private Category getCategory(Integer id) {

        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("分類"));
    }
}