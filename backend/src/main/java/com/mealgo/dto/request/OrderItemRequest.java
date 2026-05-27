package com.mealgo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {

    @NotNull
    private Integer orderId;

    @NotNull
    private Integer productId;

    @Min(1)
    private int qty = 1;

    @Min(0)
    private int price = 0;

    private String customerNote;
}