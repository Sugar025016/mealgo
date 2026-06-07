package com.mealgo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mealgo.dto.ApiResponse;
import com.mealgo.dto.request.LoginRequest;
import com.mealgo.dto.request.UserCreateRequest;
import com.mealgo.dto.response.LoginResponse;
import com.mealgo.dto.response.UserResponse;
import com.mealgo.service.IAuthService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @Operation(summary = "新增用戶")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody UserCreateRequest request) {

        UserResponse user = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("註冊成功", user));
    }

    @Operation(summary = "登入")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.success("登入成功", response));
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<Void>> verifyEmail(
            @RequestParam String token) {

        authService.verifyEmail(token);

        // return ResponseEntity.ok(
        // ApiResponse.success("信箱驗證成功，請重新登入"));
        return ResponseEntity.ok(
                ApiResponse.success("信箱驗證成功，請重新登入"));
    }
}