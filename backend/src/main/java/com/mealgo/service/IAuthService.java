package com.mealgo.service;

import com.mealgo.dto.request.LoginRequest;
import com.mealgo.dto.request.UserCreateRequest;
import com.mealgo.dto.response.LoginResponse;
import com.mealgo.dto.response.UserResponse;

public interface IAuthService {

    UserResponse register(UserCreateRequest request);

    LoginResponse login(LoginRequest request);
}
