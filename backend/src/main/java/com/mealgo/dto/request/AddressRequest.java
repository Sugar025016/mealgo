package com.mealgo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {

    @NotBlank
    private String city;

    @NotBlank
    private String area;

    @NotBlank
    private String street;

    private String detail;

    private Double lat;

    private Double lng;

    @NotNull
    private Integer userId;
}