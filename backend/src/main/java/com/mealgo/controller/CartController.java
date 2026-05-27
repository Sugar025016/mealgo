package com.mealgo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mealgo.dto.request.CartRequest;
import com.mealgo.dto.response.CartResponse;
import com.mealgo.service.ICartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    @GetMapping
    public List<CartResponse> findAll() {
        return cartService.findAll();
    }

    @GetMapping("/{id}")
    public CartResponse findById(@PathVariable Integer id) {
        return cartService.findById(id);
    }

    @PostMapping
    public CartResponse create(@Valid @RequestBody CartRequest request) {
        return cartService.create(request);
    }

    @PutMapping("/{id}")
    public CartResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody CartRequest request) {

        return cartService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        cartService.delete(id);
    }
}