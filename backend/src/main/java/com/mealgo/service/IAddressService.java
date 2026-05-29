package com.mealgo.service;

import java.util.List;

import com.mealgo.dto.request.AddressRequest;
import com.mealgo.dto.response.AddressResponse;

public interface IAddressService {

    List<AddressResponse> findByUserId(Integer userId);

    AddressResponse findByUserIdAndId(Integer userId, Integer id);

    AddressResponse create(Integer userId, AddressRequest request);

    AddressResponse update(Integer userId, Integer id, AddressRequest request);

    void delete(Integer userId, Integer id);
}