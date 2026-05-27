package com.mealgo.dto.response;

import com.mealgo.entity.Address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressResponse {

    private Integer id;

    private String city;

    private String area;

    private String street;

    private String detail;

    private Double lat;

    private Double lng;

    private Integer userId;

    public AddressResponse(Address address) {
        this.id = address.getId();
        this.city = address.getCity();
        this.area = address.getArea();
        this.street = address.getStreet();
        this.detail = address.getDetail();
        this.lat = address.getLat();
        this.lng = address.getLng();

        if (address.getUser() != null) {
            this.userId = address.getUser().getId();
        }
    }
}