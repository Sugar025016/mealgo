package com.mealgo.dto.response;

import com.mealgo.entity.OrderItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemResponse {

    private Integer id;

    private Integer orderId;

    private Integer productId;
    private String productName;

    private int qty;
    private int price;
    private int subtotal;

    private String customerNote;

    public OrderItemResponse(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.qty = orderItem.getQty();
        this.price = orderItem.getPrice();
        this.subtotal = orderItem.getQty() * orderItem.getPrice();
        this.customerNote = orderItem.getRemark();

        if (orderItem.getOrder() != null) {
            this.orderId = orderItem.getOrder().getId();
        }

        if (orderItem.getProduct() != null) {
            this.productId = orderItem.getProduct().getId();
            this.productName = orderItem.getProduct().getName();
        }
    }
}