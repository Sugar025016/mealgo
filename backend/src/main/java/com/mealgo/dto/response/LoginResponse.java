package com.mealgo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private Integer id;
    private String name;
    private String email;
    private String token;
}