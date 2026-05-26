package com.mealgo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tabs")
public class Tab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(name = "is_shelve", length = 255, nullable = false, columnDefinition = "TINYINT(1) DEFAULT false")
    private boolean isShelve;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(name = "tab_product", joinColumns = @JoinColumn(name = "tab_id"), inverseJoinColumns = @JoinColumn(name = "product_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
            "tab_id", "product_id" }))
    private List<Product> products;

    public Tab(String name, Shop shop, boolean isShelve, List<Product> products) {
        this.name = name;
        this.shop = shop;
        this.isShelve = isShelve;
        this.products = products;
    }

    @Override
    public String toString() {
        return "Tab{" +
                "id=" + id +
                ", name=" + name +
                ", isShelve=" + isShelve +
                ", products=" + products +
                '}';
    }



}
