package com.mealgo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    @NotBlank
    private String orderNumber;

    private String orderNote;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer shopId;

    @NotNull
    @Min(0)
    private Integer deliveryFee;

    @NotNull
    @Min(0)
    private Integer subtotal;

    @NotNull
    @Min(0)
    private Integer totalPrice;

    private Integer status;

    private Integer payMethod;

    @NotBlank
    private String city;

    @NotBlank
    private String area;

    @NotBlank
    private String street;

    private String detail;

    private Double lat;

    private Double lng;
}