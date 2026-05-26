package com.mealgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealgo.entity.Shop;

@Repository
public interface IShopRepository extends JpaRepository<Shop, Integer> {

	Shop getShopById(Integer id);

	List<Shop> findByIsOpenTrue();

	List<Shop> findByIsOrderableTrue();

	List<Shop> findByBrandContaining(String brand);

	List<Shop> findByUserId(Integer userId);

}