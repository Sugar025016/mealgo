package com.mealgo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mealgo.dto.request.ShopRequest;
import com.mealgo.dto.response.ShopResponse;

public interface IShopService {

    Page<ShopResponse> search(String keyword, int page, int size);

    List<ShopResponse> findAll();

    ShopResponse findById(Integer id);

    ShopResponse create(ShopRequest request);

    ShopResponse update(Integer id, ShopRequest request);

    void delete(Integer id);
}
