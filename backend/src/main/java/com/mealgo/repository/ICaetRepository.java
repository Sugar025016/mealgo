
package com.mealgo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealgo.entity.Cart;

@Repository
public interface ICaetRepository extends JpaRepository<Cart, Integer> {

}