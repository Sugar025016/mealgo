package com.mealgo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealgo.dto.request.ShopRequest;
import com.mealgo.dto.response.ShopResponse;
import com.mealgo.service.IShopService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {

    private final IShopService shopService;

    /**
     * 查詢全部店家
     */
    @GetMapping
    public List<ShopResponse> findAll() {
        return shopService.findAll();
    }

    /**
     * 查詢單一店家
     */
    @GetMapping("/{id}")
    public ShopResponse findById(
            @PathVariable Integer id) {

        return shopService.findById(id);
    }

    /**
     * 新增店家
     */
    @PostMapping
    public ShopResponse create(
            @RequestBody ShopRequest request) {

        return shopService.create(request);
    }

    /**
     * 修改店家
     */
    @PutMapping("/{id}")
    public ShopResponse update(
            @PathVariable Integer id,
            @RequestBody ShopRequest request) {

        return shopService.update(id, request);
    }

    /**
     * 刪除店家
     */
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Integer id) {

        shopService.delete(id);
    }

}
