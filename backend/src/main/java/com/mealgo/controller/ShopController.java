package com.mealgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealgo.service.IShopService;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    IShopService shopService;
    

}
