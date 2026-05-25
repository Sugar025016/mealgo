package com.mealgo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mealgo.entity.Product;


@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.shop.id = :shopId AND p.isDelete = false")
    List<Product> findByShopId(@Param("shopId") int shopId);

    @Query("SELECT p FROM Product p WHERE p.shop.id = :shopId AND p.isDelete = false")
    Page<Product> findByShopId(@Param("shopId") int shopId, Pageable pageable);



}