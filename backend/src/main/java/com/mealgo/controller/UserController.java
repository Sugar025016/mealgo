package com.mealgo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealgo.dto.ApiResponse;
import com.mealgo.dto.request.PasswordRequest;
import com.mealgo.dto.request.UserUpdateRequest;
import com.mealgo.dto.response.UserResponse;
import com.mealgo.security.CustomUserDetails;
import com.mealgo.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User", description = "用戶 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    // @Operation(summary = "查詢全部用戶")
    // @GetMapping
    // public ResponseEntity<ApiResponse<List<UserResponse>>> findAll() {

    // List<UserResponse> users = userService.findAll();

    // return ResponseEntity.ok(
    // ApiResponse.success("查詢成功", users));
    // }

    @Operation(summary = "查詢單一用戶")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> findById(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        UserResponse user = userService.findById(userDetails.getId());

        return ResponseEntity.ok(
                ApiResponse.success("查詢成功", user));
    }

    @Operation(summary = "修改用戶")
    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UserUpdateRequest request) {

        UserResponse user = userService.update(userDetails.getId(), request);

        return ResponseEntity.ok(
                ApiResponse.success("修改成功", user));
    }

    @Operation(summary = "修改密碼")
    @PatchMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody PasswordRequest request) {

        userService.updatePassword(
                userDetails.getId(),
                request);

        return ResponseEntity.ok(
                ApiResponse.success("密碼修改成功"));
    }

    // @Operation(summary = "刪除用戶")
    // @DeleteMapping("/{id}")
    // public ResponseEntity<ApiResponse<Void>> delete(
    // @PathVariable Integer id) {

    // userService.delete(id);

    // return ResponseEntity.ok(
    // ApiResponse.success("刪除成功"));
    // }
}