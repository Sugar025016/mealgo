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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
    @Column(name = "delivery_km")
    private Double deliveryKm = 0.0;
    @Column(name = "delivery_price")
    private Integer deliveryPrice = 0;

    @Column(name = "is_open", nullable = false)
    private boolean isOpen;
    @Column(name = "is_orderable", nullable = false)
    private boolean isOrderable;

    // 圖片
    @Column(name = "image_path", length = 255)
    private String imagePath;

    // 地址
    @Column(name = "city", length = 20, nullable = false)
    private String city;
    @Column(name = "area", length = 20, nullable = false)
    private String area;
    @Column(name = "street", length = 100, nullable = false)
    private String street;
    @Column(name = "detail", length = 255, nullable = false)
    private String detail;
    @Column(name = "lat")
    private Double lat = 0.0;
    @Column(name = "lng")
    private Double lng = 0.0;

    @JsonIgnore
    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<Product> products;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "shop_category", joinColumns = @JoinColumn(name = "shop_id"), inverseJoinColumns = @JoinColumn(name = "category_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
            "shop_id", "category_id" }))
    private List<Category> categories;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // @JsonIgnore
    // @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy =
    // "loves")
    // private List<User> loves;

    // @JsonIgnore
    // @OneToMany(cascade = CascadeType.ALL, mappedBy = "shop")
    // private List<Order> orders;

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

    // private void setIsOpen(boolean isOpen) {
    // this.isOpen = isOpen;
    // if (!this.isOpen) {
    // this.isOrderable = false;
    // }
    // }

}
