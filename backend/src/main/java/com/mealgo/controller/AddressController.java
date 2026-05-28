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
import com.mealgo.dto.request.AddressRequest;
import com.mealgo.dto.response.AddressResponse;
import com.mealgo.service.IAddressService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "Address", description = "地址 API")
@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final IAddressService addressService;

    @Operation(summary = "查詢全部地址")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponse>>> findAll() {

        List<AddressResponse> addresses = addressService.findAll();

        return ResponseEntity.ok(
                ApiResponse.success("查詢成功", addresses));
    }

    @Operation(summary = "查詢單一地址")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> findById(
            @PathVariable Integer id) {

        AddressResponse address = addressService.findById(id);

        return ResponseEntity.ok(
                ApiResponse.success("查詢成功", address));
    }

    @Operation(summary = "新增地址")
    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponse>> create(
            @Valid @RequestBody AddressRequest request) {

        AddressResponse address = addressService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success("新增成功", address));
    }

    @Operation(summary = "修改地址")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody AddressRequest request) {

        AddressResponse address = addressService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("修改成功", address));
    }

    @Operation(summary = "刪除地址")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Integer id) {

        addressService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success("刪除成功"));
    }
}