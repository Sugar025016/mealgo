package com.mealgo.service;

import java.util.List;

import com.mealgo.dto.request.CartItemRequest;
import com.mealgo.dto.response.CartItemResponse;

public interface ICartItemService {

    List<CartItemResponse> findAll();

    List<CartItemResponse> findByCartId(Integer cartId);

    CartItemResponse findById(Integer id);

    CartItemResponse create(CartItemRequest request);

    CartItemResponse update(Integer id, CartItemRequest request);

    void delete(Integer id);
}