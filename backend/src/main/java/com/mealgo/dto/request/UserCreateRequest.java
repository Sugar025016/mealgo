package com.mealgo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {

    @Schema(description = "使用者姓名", example = "測試使用者3")
    @NotBlank
    private String name;

    @Schema(description = "使用者電話", example = "0911111111")
    @NotBlank
    private String phone;

    @Schema(description = "使用者電子郵件", example = "user@gmail.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "使用者密碼", example = "123456")
    @NotBlank
    @Size(min = 6, max = 50)
    private String password;
}
