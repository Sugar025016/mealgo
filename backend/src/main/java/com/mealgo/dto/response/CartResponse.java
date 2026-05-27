package com.mealgo.dto.response;

import com.mealgo.entity.Cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartResponse {

    private Integer id;

    private Integer userId;
    private String userName;

    private Integer shopId;
    private String shopName;

    public CartResponse(Cart cart) {
        this.id = cart.getId();

        if (cart.getUser() != null) {
            this.userId = cart.getUser().getId();
            this.userName = cart.getUser().getName();
        }

        if (cart.getShop() != null) {
            this.shopId = cart.getShop().getId();
            this.shopName = cart.getShop().getBrand();
        }
    }
}