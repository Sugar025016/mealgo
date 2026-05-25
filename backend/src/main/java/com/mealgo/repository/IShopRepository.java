package com.mealgo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealgo.entity.Shop;

@Repository
public interface IShopRepository extends JpaRepository<Shop, Integer> {

	Shop getShopById(Integer id);

}	