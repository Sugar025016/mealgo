package com.mealgo.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mealgo.dto.request.LoginRequest;
import com.mealgo.dto.request.UserCreateRequest;
import com.mealgo.dto.response.LoginResponse;
import com.mealgo.dto.response.UserResponse;
import com.mealgo.entity.CartItem;
import com.mealgo.entity.User;
import com.mealgo.entity.VerificationToken;
import com.mealgo.enums.VerificationType;
import com.mealgo.exception.BadRequestException;
import com.mealgo.exception.ResourceNotFoundException;
import com.mealgo.repository.IUserRepository;
import com.mealgo.repository.IVerificationTokenRepository;
import com.mealgo.security.JwtUtil;
import com.mealgo.service.IAuthService;
import com.mealgo.service.IEmailService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final IVerificationTokenRepository verificationTokenRepository;
    private final IEmailService emailService;

    @Override
    @Transactional
    public UserResponse register(UserCreateRequest request) {

        userRepository.findByEmail(request.getEmail())
                .ifPresent(existsUser -> {

                    if (existsUser.getPassword() == null
                            || existsUser.getPassword().isBlank()) {
                        throw new BadRequestException(
                                "此 Email 已使用 Google 登入註冊，請使用 Google 登入");
                    }

                    if (existsUser.getEmailVerifiedAt() == null) {
                        throw new BadRequestException(
                                "此信箱尚未完成驗證，請重新寄送驗證信");
                    }

                    throw new BadRequestException("Email 已註冊");
                });
        User user = new User();
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        User savedUser = userRepository.save(user);
        VerificationToken verificationToken = createToken(savedUser, VerificationType.EMAIL_VERIFY);
        emailService.sendVerifyEmail(savedUser, verificationToken.getToken());

        log.info(
                "User Register Success | userId={} | email={}",
                savedUser.getId(),
                savedUser.getEmail());

        return new UserResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("帳號或密碼錯誤"));
        // System.out.println("user.getPassword() = " + user.getPassword());
        // System.out.println("request.getPassword() = " + request.getPassword());
        // System.out.println(new BCryptPasswordEncoder().encode("123456"));
        if (user.getEmailVerifiedAt() == null) {
            throw new BadRequestException("請先完成信箱驗證");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("帳號或密碼錯誤");
        }

        String token = jwtUtil.generateToken(user);

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                token);
    }

    @Override
    @Transactional
    public void verifyEmail(String token) {

        VerificationToken verificationToken = verificationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new BadRequestException("驗證連結無效"));

        if (verificationToken.getUsedAt() != null) {
            throw new BadRequestException("驗證連結已使用");
        }

        if (verificationToken.getExpireAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("驗證連結已過期");
        }

        User user = verificationToken.getUser();

        user.setEmailVerifiedAt(LocalDateTime.now());

        verificationToken.setUsedAt(LocalDateTime.now());

        userRepository.save(user);
        verificationTokenRepository.save(verificationToken);

        log.info(
                "Email Verify Success | userId={} | email={}",
                user.getId(),
                user.getEmail());
    }

    private VerificationToken createToken(User user, VerificationType type) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setType(type);
        verificationToken.setExpireAt(LocalDateTime.now().plusMinutes(30));

        return verificationTokenRepository.save(verificationToken);
    }

    // private User findByEmail(String email) {
    // return userRepository.findByEmail(email)
    // .orElseThrow(() -> new ResourceNotFoundException("用戶"));
    // }

    // public static BodyBuilder status(HttpStatusCode status) {
    // Assert.notNull(status, "HttpStatusCode must not be null");
    // return new DefaultBuilder(status);
    // }

}