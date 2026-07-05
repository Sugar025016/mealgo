
package com.mealgo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mealgo.entity.Cart;

public interface ICartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUserIdAndShopId(Integer userId, Integer shopId);

    List<Cart> findAllByUserId(Integer userId);

    Optional<Cart> findByUserIdAndId(Integer userId, Integer id);
}