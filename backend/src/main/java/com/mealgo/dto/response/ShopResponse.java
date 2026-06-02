package com.mealgo.dto.response;

import java.io.Serializable;

import com.mealgo.entity.Shop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShopResponse implements Serializable {

    private Integer id;

    private String brand;

    private String branch;

    private String phone;

    private String description;

    private Double deliveryKm;

    private Integer deliveryPrice;

    private boolean isOrderable;

    private boolean isOpen;

    private String city;

    private String area;

    private String street;

    private String detail;

    private Double lat;

    private Double lng;

    public ShopResponse(Shop shop) {

        this.id = shop.getId();
        this.brand = shop.getBrand();
        this.branch = shop.getBranch();
        this.phone = shop.getPhone();
        this.description = shop.getDescription();
        this.deliveryKm = shop.getDeliveryKm();
        this.deliveryPrice = shop.getDeliveryPrice();
        this.isOrderable = shop.isOrderable();
        this.isOpen = shop.isOpen();

        this.city = shop.getCity();
        this.area = shop.getArea();
        this.street = shop.getStreet();
        this.detail = shop.getDetail();

        this.lat = shop.getLat();
        this.lng = shop.getLng();

    }
}