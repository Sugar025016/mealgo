package com.mealgo.service;

import java.util.List;

import com.mealgo.dto.request.CartRequest;
import com.mealgo.dto.response.CartResponse;

public interface ICartService {

    List<CartResponse> findAll();

    CartResponse findById(Integer id);

    CartResponse create(CartRequest request);

    CartResponse update(Integer id, CartRequest request);

    void delete(Integer id);
}