package com.mealgo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mealgo.dto.ApiResponse;
import com.mealgo.dto.request.AddressRequest;
import com.mealgo.dto.response.AddressResponse;
import com.mealgo.service.IAddressService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final IAddressService addressService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponse>>> findAll() {

        List<AddressResponse> addresses = addressService.findAll();

        return ResponseEntity.ok(
                ApiResponse.success("查詢成功", addresses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> findById(
            @PathVariable Integer id) {

        AddressResponse address = addressService.findById(id);

        return ResponseEntity.ok(
                ApiResponse.success("查詢成功", address));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponse>> create(
            @Valid @RequestBody AddressRequest request) {

        AddressResponse address = addressService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success("新增成功", address));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody AddressRequest request) {

        AddressResponse address = addressService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("修改成功", address));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Integer id) {

        addressService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success("刪除成功"));
    }
}