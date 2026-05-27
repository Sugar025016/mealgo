package com.mealgo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {

    @NotNull
    private Integer cartId;

    @NotNull
    private Integer productId;

    @Min(1)
    private int qty = 1;

    private String remark;
}