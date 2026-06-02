package com.mealgo.dto.response;

import java.io.Serializable;

import com.mealgo.entity.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponse implements Serializable {

    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private String imagePath;
    private boolean isOrderable;

    private Integer shopId;
    private String shopName;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imagePath = product.getImagePath();
        this.isOrderable = product.isShelve();

        if (product.getShop() != null) {
            this.shopId = product.getShop().getId();
            this.shopName = product.getShop().getBrand();
        }
    }
}
