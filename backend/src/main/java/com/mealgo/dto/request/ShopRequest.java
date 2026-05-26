package com.mealgo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopRequest {

    @NotBlank
    private String brand;

    private String branch;

    @NotBlank
    private String phone;

    private String description;

    @NotNull
    private Double deliveryKm;

    @NotNull
    private Integer deliveryPrice;

    @NotNull
    private Boolean isOrderable;

    @NotNull
    private Boolean isOpen;

    @NotBlank
    private String city;

    @NotBlank
    private String area;

    @NotBlank
    private String street;

    @NotBlank
    private String detail;

    private Double lat;

    private Double lng;
}