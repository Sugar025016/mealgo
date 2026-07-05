package com.mealgo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mealgo.entity.Product;

public interface IProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByShopId(Integer shopId);

    Optional<Product> findByShopIdAndId(Integer shopId, Integer productId);

}