package com.mealgo.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "shops")
public class Shop extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "brand", length = 50, nullable = false)
    private String brand;
    @Column(name = "branch", length = 100)
    private String branch;
    @Column(name = "phone", length = 20, nullable = false)
    private String phone;
    @Column(name = "description", length = 512)
    private String description;

    @Column(name = "is_orderable", nullable = false)
    private boolean isOrderable;
    @Column(name = "is_open", nullable = false)
    private boolean isOpen;

    @Column(name = "delivery_km")
    private Double deliveryKm = 0.0;
    @Column(name = "delivery_price")
    private Integer deliveryPrice = 0;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // 地址
    @Column(name = "city", length = 20)
    private String city;
    @Column(name = "area", length = 20)
    private String area;
    @Column(name = "street", length = 100)
    private String street;
    @Column(name = "detail", length = 255)
    private String detail;
    @Column(name = "lat")
    private Double lat = 0.0;
    @Column(name = "lng")
    private Double lng = 0.0;

    @JsonIgnore
    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<Product> products;

    // 給關聯過來的回傳值
    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", brand=" + brand +
                ", branch=" + branch +
                ", phone=" + phone +
                ", description=" + description +
                '}';
    }

    // public void setIsDelete(boolean isDelete, boolean isOpen, boolean
    // isOrderable) {
    // this.isOrderable = isOrderable;
    // setIsOpen(isOpen);
    // setIsDelete(isDelete);
    // }

    public void close() {
        this.isOpen = false;
        this.isOrderable = false;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
        close();
    }

    // private void setIsOpen(boolean isOpen) {
    // this.isOpen = isOpen;
    // if (!this.isOpen) {
    // this.isOrderable = false;
    // }
    // }

}
