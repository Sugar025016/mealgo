package com.mealgo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank(message = "產品名稱不可為空")
    private String name;

    private String description;

    @NotNull(message = "價格不可為空")
    @Min(value = 0, message = "價格必須大於等於0")
    private Integer price;

    private String imagePath;

    private Boolean isShelve = true;

    @NotNull
    private Integer shopId;
}