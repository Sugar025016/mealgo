package com.mealgo.service;

import java.util.List;

import com.mealgo.dto.request.ShopRequest;
import com.mealgo.dto.response.ShopResponse;

public interface IShopService {

    List<ShopResponse> findAll();

    ShopResponse findById(Integer id);

    ShopResponse create(ShopRequest request);

    ShopResponse update(Integer id, ShopRequest request);

    void delete(Integer id);
}
