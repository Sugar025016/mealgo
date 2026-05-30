package com.mealgo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Schema(description = "使用者 Email", example = "shop@test.com")
    @NotBlank(message = "Email不能為空")
    @Email(message = "Email格式錯誤")
    private String email;

    @Schema(description = "密碼", example = "123456")
    @NotBlank(message = "密碼不能為空")
    private String password;

}