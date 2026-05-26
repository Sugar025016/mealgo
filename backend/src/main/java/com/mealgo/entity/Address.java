package com.mealgo.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // @JsonIgnore
    // @OneToOne(mappedBy = "addressDelivery", cascade = CascadeType.ALL, fetch =
    // FetchType.LAZY)
    // private User userDelivery;

    // @JsonIgnore
    // @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    // private User loveUsers;

    @Override
    public String toString() {
        return "AddressData{" +
                "id=" + id +
                ", city=" + city +
                ", area=" + area +
                ", street=" + street +
                ", detail=" + detail +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
