package com.mealgo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "姓名不可為空")
    private String name;

    private String phone;

    @Email(message = "電子郵件格式不正確")
    @NotBlank(message = "電子郵件不可為空")
    private String email;

    @NotBlank(message = "密碼不可為空")
    private String password;
}