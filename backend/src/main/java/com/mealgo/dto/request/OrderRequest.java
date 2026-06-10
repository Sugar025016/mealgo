package com.mealgo.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderRequest {

    @NotNull(message = "購物車不可為空")
    private Integer cartId;

    @NotNull(message = "付款方式不可為空")
    private Integer payMethod;

    @NotNull(message = "地址ID不可為空")
    private Integer addressId;

    @Size(max = 200, message = "備註不可超過200字")
    private String orderNote;

}