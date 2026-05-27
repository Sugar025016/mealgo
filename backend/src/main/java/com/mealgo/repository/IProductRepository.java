package com.mealgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealgo.entity.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByShopId(Integer shopId);

}