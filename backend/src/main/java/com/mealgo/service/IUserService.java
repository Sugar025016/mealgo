package com.mealgo.service;

import java.util.List;

import com.mealgo.dto.request.PasswordRequest;
import com.mealgo.dto.request.UserCreateRequest;
import com.mealgo.dto.request.UserUpdateRequest;
import com.mealgo.dto.response.UserResponse;

public interface IUserService {

    List<UserResponse> findAll();

    UserResponse findById(Integer id);

    UserResponse create(UserCreateRequest request);

    UserResponse update(Integer id, UserUpdateRequest request);

    void updatePassword(Integer id, PasswordRequest request);

    void delete(Integer id);
}