package com.mealgo.entity;

import java.util.List;

import com.mealgo.enums.PayMethod;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "order_number", length = 50, nullable = false, unique = true)
    private String orderNumber;
    @Column(name = "order_note", length = 512)
    private String orderNote;

    @Column(name = "delivery_price", nullable = false)
    private int deliveryPrice;
    @Column(name = "subtotal", nullable = false)
    private int subtotal;
    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "pay_method", nullable = false)
    private int payMethod = PayMethod.CASH.getCode();
    @Column(name = "status", nullable = true)
    private int status = 11;

    // 外送地址
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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

}
