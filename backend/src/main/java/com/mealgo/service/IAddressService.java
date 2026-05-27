package com.mealgo.service;

import java.util.List;

import com.mealgo.dto.request.AddressRequest;
import com.mealgo.dto.response.AddressResponse;

public interface IAddressService {

    List<AddressResponse> findAll();

    AddressResponse findById(Integer id);

    AddressResponse create(AddressRequest request);

    AddressResponse update(Integer id, AddressRequest request);

    void delete(Integer id);
}