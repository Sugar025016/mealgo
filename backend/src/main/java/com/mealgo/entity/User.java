package com.mealgo.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", length = 255, nullable = false)
    private String name;
    @Column(name = "phone", length = 11)
    private String phone;
    @Email
    @Column(name = "account", length = 64, nullable = false, unique = true)
    private String account;
    @Column(name = "password", length = 32, nullable = false)
    private String password;

    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;

    @Column(name = "verify_token", length = 255)
    private String verifyToken;

    @Column(name = "verify_expire_at")
    private LocalDateTime verifyExpireAt;

    // @Email
    // @Column(name = "email", length = 255)
    // private String email;

    // @JsonIgnore
    // @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    // @JoinTable(name = "user_address", joinColumns = @JoinColumn(name =
    // "user_id"), inverseJoinColumns = @JoinColumn(name = "address_id"),
    // uniqueConstraints = @UniqueConstraint(columnNames = {
    // "user_id", "address_id" }))
    // private List<Address> addresses;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Address> addresses;

    // @ManyToMany(cascade = CascadeType.ALL)
    // @JoinTable(name = "love", joinColumns = @JoinColumn(name = "user_id"),
    // inverseJoinColumns = @JoinColumn(name = "shop_id"), uniqueConstraints =
    // @UniqueConstraint(columnNames = {
    // "user_id", "shop_id" }))
    // private List<Shop> loves;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Shop> shops;

    // @JsonIgnore
    // @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    // private List<Order> orders;

    // 給關聯過來的回傳值
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name=" + name +
                ", name=" + name +
                ", phone=" + phone +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // public void setAddressDelivery(Address address) {
    // Optional<Address> findAny = addresses.stream().filter(v -> v.getId() ==
    // address.getId()).findAny();
    // Address orElseThrow = findAny.orElseThrow(() -> new
    // ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    // this.AddressDelivery = orElseThrow;
    // }



    // public Set<ShopResponse> getShopLoveList() {
    // Set<ShopResponse> collect = shopLoveList.stream().map(v->new
    // ShopResponse(v)).collect(Collectors.toSet());
    // return collect;
    // }

    // public void setShopLoveList(Set<Shop> shopLoveList) {
    // this.shopLoveList = shopLoveList;
    // }

}
