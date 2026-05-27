package com.mealgo.dto.response;

import com.mealgo.entity.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponse {

    private Integer id;
    private String orderNumber;
    private String orderNote;

    private int deliveryFee;
    private int subtotal;
    private int totalPrice;
    private int status;
    private int payMethod;

    private String city;
    private String area;
    private String street;
    private String detail;
    private Double lat;
    private Double lng;

    private Integer userId;
    private String userName;

    private Integer shopId;
    private String shopName;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.orderNumber = order.getOrderNumber();
        this.orderNote = order.getOrderNote();

        this.deliveryFee = order.getDeliveryFee();
        this.subtotal = order.getSubtotal();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
        this.payMethod = order.getPayMethod();

        this.city = order.getCity();
        this.area = order.getArea();
        this.street = order.getStreet();
        this.detail = order.getDetail();
        this.lat = order.getLat();
        this.lng = order.getLng();

        if (order.getUser() != null) {
            this.userId = order.getUser().getId();
            this.userName = order.getUser().getName();
        }

        if (order.getShop() != null) {
            this.shopId = order.getShop().getId();
            this.shopName = order.getShop().getBrand();
        }
    }
}