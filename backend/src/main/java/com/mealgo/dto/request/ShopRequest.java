package com.mealgo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShopRequest {

    @NotBlank(message = "商店名稱不可為空")
    private String brand;

    private String branch;

    @NotBlank(message = "電話不可為空")
    private String phone;

    private String description;

    @NotNull(message = "營業開始時間不可為空")
    private Double deliveryKm;

    @NotNull(message = "運費不可為空")
    private Integer deliveryPrice;

    @NotNull(message = "是否可訂餐不可為空")
    private Boolean isOrderable;

    @NotNull(message = "是否營業不可為空")
    private Boolean isOpen;

    @NotBlank(message = "城市不可為空")
    private String city;

    @NotBlank(message = "區域不可為空")
    private String area;

    @NotBlank(message = "街道不可為空")
    private String street;

    @NotBlank(message = "詳細地址不可為空")
    private String detail;

    private Double lat;

    private Double lng;
}