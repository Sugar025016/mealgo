package com.mealgo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealgo.dto.ApiResponse;
import com.mealgo.dto.request.UserRequest;
import com.mealgo.dto.response.UserResponse;
import com.mealgo.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "User", description = "用戶 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @Operation(summary = "查詢全部用戶")
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> findAll() {

        List<UserResponse> users = userService.findAll();

        return ResponseEntity.ok(
                ApiResponse.success("查詢成功", users));
    }

    @Operation(summary = "查詢單一用戶")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> findById(
            @PathVariable Integer id) {

        UserResponse user = userService.findById(id);

        return ResponseEntity.ok(
                ApiResponse.success("查詢成功", user));
    }

    @Operation(summary = "新增用戶")
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(
            @Valid @RequestBody UserRequest request) {

        UserResponse user = userService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success("新增成功", user));
    }

    @Operation(summary = "修改用戶")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserRequest request) {

        UserResponse user = userService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("修改成功", user));
    }

    @Operation(summary = "刪除用戶")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Integer id) {

        userService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success("刪除成功"));
    }
}