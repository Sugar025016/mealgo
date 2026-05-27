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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> findAll() {

        List<UserResponse> users = userService.findAll();

        return ResponseEntity.ok(
                ApiResponse.success("查詢成功", users)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> findById(
            @PathVariable Integer id) {

        UserResponse user = userService.findById(id);

        return ResponseEntity.ok(
                ApiResponse.success("查詢成功", user)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(
            @Valid @RequestBody UserRequest request) {

        UserResponse user = userService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success("新增成功", user)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserRequest request) {

        UserResponse user = userService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("修改成功", user)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Integer id) {

        userService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success("刪除成功")
        );
    }
}