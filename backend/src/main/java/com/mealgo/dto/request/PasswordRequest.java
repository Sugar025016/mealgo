package com.mealgo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRequest {

    @NotBlank(message = "舊密碼不能為空")
    private String oldPassword;

    @NotBlank(message = "新密碼不能為空")
    @Size(min = 6, max = 50, message = "密碼長度需介於6~50字")
    private String newPassword;
}