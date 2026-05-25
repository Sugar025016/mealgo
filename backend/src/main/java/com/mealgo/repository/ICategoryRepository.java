package com.mealgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.mealgo.entity.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {

    Category getCategoryById(int id);

    // Category getCategoryById(Integer id);
    // @Modifying
    // int deleteByCategory(Category category);

    List<Category> getCategoryByIdIn(List<Integer> categoryIds);

    @Modifying
    void deleteById(int id);
}
