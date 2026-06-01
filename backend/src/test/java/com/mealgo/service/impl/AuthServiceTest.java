package com.mealgo.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mealgo.dto.request.LoginRequest;
import com.mealgo.dto.request.UserCreateRequest;
import com.mealgo.dto.response.LoginResponse;
import com.mealgo.dto.response.UserResponse;
import com.mealgo.entity.User;
import com.mealgo.exception.BadRequestException;
import com.mealgo.repository.IUserRepository;
import com.mealgo.security.JwtUtil;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_emailNotExists_shouldCreateUser() {
        // 建立測試資料
        UserCreateRequest request = new UserCreateRequest();
        request.setName("Jessie");
        request.setPhone("0912345678");
        request.setEmail("test@example.com");
        request.setPassword("123456");

        // 模擬userRepository.save()回傳的User物件
        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setName("Jessie");
        savedUser.setPhone("0912345678");
        savedUser.setEmail("test@example.com");
        savedUser.setPassword("encodedPassword");
        savedUser.setRole("USER");

        // 設定模擬行為
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // 執行測試方法
        UserResponse response = authService.register(request);

        // 驗證結果
        assertThat(response).isNotNull();

        // 使用ArgumentCaptor捕捉傳入userRepository.save()的User物件
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        // 驗證userRepository.save()被呼叫一次，並捕捉參數
        verify(userRepository).save(userCaptor.capture());

        // 取得被捕捉的User物件
        User userToSave = userCaptor.getValue();

        // 驗證User物件的屬性
        assertThat(userToSave.getName()).isEqualTo("Jessie");
        assertThat(userToSave.getPhone()).isEqualTo("0912345678");
        assertThat(userToSave.getEmail()).isEqualTo("test@example.com");
        assertThat(userToSave.getPassword()).isEqualTo("encodedPassword");
        assertThat(userToSave.getRole()).isEqualTo("USER");

        // 驗證回傳的UserResponse物件
        verify(userRepository).existsByEmail("test@example.com");
        verify(passwordEncoder).encode("123456");
    }

    @Test
    void register_emailAlreadyExists_shouldThrowBadRequestException() {
        // 建立測試資料
        UserCreateRequest request = new UserCreateRequest();
        request.setEmail("test@example.com");

        // 設定模擬行為
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // 執行測試方法並驗證例外
        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Email 已被使用");

        // 驗證userRepository.existsByEmail()被呼叫一次，且userRepository.save()和passwordEncoder.encode()不被呼叫
        verify(userRepository).existsByEmail("test@example.com");
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void login_emailAndPasswordCorrect_shouldReturnLoginResponse() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("123456");

        User user = new User();
        user.setId(1);
        user.setName("Jessie");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken(user)).thenReturn("jwt-token");

        LoginResponse response = authService.login(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getName()).isEqualTo("Jessie");
        assertThat(response.getEmail()).isEqualTo("test@example.com");
        assertThat(response.getToken()).isEqualTo("jwt-token");

        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).matches("123456", "encodedPassword");
        verify(jwtUtil).generateToken(user);
    }

    @Test
    void login_emailNotFound_shouldThrowBadRequestException() {
        LoginRequest request = new LoginRequest();
        request.setEmail("notfound@example.com");
        request.setPassword("123456");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("帳號或密碼錯誤");

        verify(userRepository).findByEmail("notfound@example.com");
        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtUtil, never()).generateToken(any());
    }

    @Test
    void login_passwordWrong_shouldThrowBadRequestException() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrongPassword");

        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("帳號或密碼錯誤");

        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).matches("wrongPassword", "encodedPassword");
        verify(jwtUtil, never()).generateToken(any());
    }
}