package com.mealgo.service;

import java.util.List;

import com.mealgo.dto.request.ProductRequest;
import com.mealgo.dto.response.ProductResponse;

public interface IProductService {

    List<ProductResponse> findAll();

    List<ProductResponse> findByShopId(Integer shopId);

    ProductResponse findById(Integer id);

    ProductResponse create(ProductRequest request);

    ProductResponse update(Integer id, ProductRequest request);

    void delete(Integer shopId, Integer id);
}
