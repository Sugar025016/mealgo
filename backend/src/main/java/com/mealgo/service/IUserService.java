package com.mealgo.service;

import java.util.List;

import com.mealgo.dto.request.UserRequest;
import com.mealgo.dto.response.UserResponse;

public interface IUserService {

    List<UserResponse> findAll();

    UserResponse findById(Integer id);

    UserResponse create(UserRequest request);

    UserResponse update(Integer id, UserRequest request);

    void delete(Integer id);
}