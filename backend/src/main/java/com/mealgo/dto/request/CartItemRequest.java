package com.mealgo.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CartItemRequest {

    @Min(value = 1, message = "數量必須大於0")
    private int qty = 1;

    private String remark;
}