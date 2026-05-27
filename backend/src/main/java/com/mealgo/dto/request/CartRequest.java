package com.mealgo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer shopId;
}