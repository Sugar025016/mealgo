package com.mealgo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderItemRequest {

    @NotNull(message = "訂單ID不可為空")
    private Integer orderId;

    @NotNull(message = "商品ID不可為空")
    private Integer productId;

    @NotNull(message = "數量不可為空")
    @Min(value = 1, message = "數量必須大於0")
    private Integer qty;

    @Size(max = 200, message = "備註不可超過200字")
    private String customerNote;
}