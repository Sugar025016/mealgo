package com.mealgo.dto.response;

import com.mealgo.entity.CartItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemResponse {

    private Integer id;

    private Integer cartId;

    private Integer productId;
    private String productName;
    private Integer productPrice;

    private int qty;
    private String remark;

    public CartItemResponse(CartItem cartItem) {
        this.id = cartItem.getId();
        this.qty = cartItem.getQty();
        this.remark = cartItem.getRemark();

        if (cartItem.getCart() != null) {
            this.cartId = cartItem.getCart().getId();
        }

        if (cartItem.getProduct() != null) {
            this.productId = cartItem.getProduct().getId();
            this.productName = cartItem.getProduct().getName();
            this.productPrice = cartItem.getProduct().getPrice();
        }
    }
}