package com.mealgo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CartRequest {

    @NotNull(message = "商店ID不可為空")
    private Integer shopId;

    @NotNull(message = "商品ID不可為空")
    private Integer productId;

    @NotNull(message = "商品數量不可為空")
    @Min(value = 1, message = "商品數量必須大於0")
    private Integer qty;

    @Size(max = 200, message = "備註不可超過200字")
    private String remark;
}