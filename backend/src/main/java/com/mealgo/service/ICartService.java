package com.mealgo.service;

import java.util.List;

import com.mealgo.dto.request.CartRequest;
import com.mealgo.dto.response.CartResponse;

public interface ICartService {

    // List<CartResponse> findAll();

    List<CartResponse> findAllByUserId(Integer userId);

    CartResponse findByUserIdAndId(Integer userId, Integer id);

    CartResponse create(Integer userId, CartRequest request);

    void delete(Integer userId, Integer id);
}