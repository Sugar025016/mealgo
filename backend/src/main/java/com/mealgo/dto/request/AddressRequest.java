package com.mealgo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressRequest {

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

    @NotNull(message = "用戶ID不可為空")
    private Integer userId;

}