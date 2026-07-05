package com.mealgo.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
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
import com.mealgo.entity.VerificationToken;
// import com.mealgo.enums.VerificationType;
import com.mealgo.exception.BadRequestException;
import com.mealgo.repository.IUserRepository;
import com.mealgo.repository.IVerificationTokenRepository;
import com.mealgo.security.JwtUtil;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private IVerificationTokenRepository verificationTokenRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_emailNotExists_shouldCreateUser() {
        UserCreateRequest request = new UserCreateRequest();
        request.setName("Jessie");
        request.setPhone("0912345678");
        request.setEmail("test@example.com");
        request.setPassword("123456");

        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setName("Jessie");
        savedUser.setPhone("0912345678");
        savedUser.setEmail("test@example.com");
        savedUser.setPassword("encodedPassword");
        savedUser.setRole("USER");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        VerificationToken token = new VerificationToken();
        token.setToken("test-token");
        when(verificationTokenRepository.save(any()))
                .thenReturn(token);

        UserResponse response = authService.register(request);

        assertThat(response).isNotNull();

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userCaptor.capture());

        User userToSave = userCaptor.getValue();

        assertThat(userToSave.getName()).isEqualTo("Jessie");
        assertThat(userToSave.getPhone()).isEqualTo("0912345678");
        assertThat(userToSave.getEmail()).isEqualTo("test@example.com");
        assertThat(userToSave.getPassword()).isEqualTo("encodedPassword");
        assertThat(userToSave.getRole()).isEqualTo("USER");

        verify(userRepository).existsByEmail("test@example.com");
        verify(passwordEncoder).encode("123456");
        verify(verificationTokenRepository).save(any());
    }

    @Test
    void register_emailAlreadyExists_shouldThrowBadRequestException() {
        UserCreateRequest request = new UserCreateRequest();
        request.setEmail("test@example.com");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Email 已被使用");

        verify(userRepository).existsByEmail("test@example.com");
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(any());
        verify(verificationTokenRepository, never()).save(any());
    }

    @Test
    void login_emailAndPasswordCorrect_shouldReturnLoginResponse() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("123456");

        User user = createVerifiedUser();

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

        User user = createVerifiedUser();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("帳號或密碼錯誤");

        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).matches("wrongPassword", "encodedPassword");
        verify(jwtUtil, never()).generateToken(any());
    }

    @Test
    void login_emailNotVerified_shouldThrowBadRequestException() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("123456");

        User user = createUnverifiedUser();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("請先完成信箱驗證");

        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtUtil, never()).generateToken(any());
    }

    private User createVerifiedUser() {
        User user = new User();
        user.setId(1);
        user.setName("Jessie");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setEmailVerifiedAt(LocalDateTime.now());
        return user;
    }

    private User createUnverifiedUser() {
        User user = new User();
        user.setId(1);
        user.setName("Jessie");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setEmailVerifiedAt(null);
        return user;
    }

    // private VerificationToken createToken(
    // User user,
    // VerificationType type) {

    // VerificationToken token = new VerificationToken();

    // token.setUser(user);
    // token.setType(type);

    // return verificationTokenRepository.save(token);
    // }
}