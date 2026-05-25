
package com.mealgo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealgo.entity.CartItem;

@Repository
public interface ICaetItemRepository extends JpaRepository<CartItem, Integer> {

}