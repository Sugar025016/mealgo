
package com.mealgo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealgo.entity.Cart;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUserIdAndShopId(Integer userId, Integer shopId);
}