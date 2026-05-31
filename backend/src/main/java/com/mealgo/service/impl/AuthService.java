package com.mealgo.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mealgo.dto.request.LoginRequest;
import com.mealgo.dto.request.UserCreateRequest;
import com.mealgo.dto.response.LoginResponse;
import com.mealgo.dto.response.UserResponse;
import com.mealgo.entity.User;
import com.mealgo.exception.BadRequestException;
import com.mealgo.repository.IUserRepository;
import com.mealgo.security.JwtUtil;
import com.mealgo.service.IAuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponse register(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email 已被使用");
        }
        User user = new User();
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        User savedUser = userRepository.save(user);

        log.info(
                "User Register Success | userId={} | email={}",
                savedUser.getId(),
                savedUser.getEmail());

        return new UserResponse(savedUser);
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("帳號或密碼錯誤"));
        // System.out.println("user.getPassword() = " + user.getPassword());
        // System.out.println("request.getPassword() = " + request.getPassword());
        // System.out.println(new BCryptPasswordEncoder().encode("123456"));
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

    // public static BodyBuilder status(HttpStatusCode status) {
    // Assert.notNull(status, "HttpStatusCode must not be null");
    // return new DefaultBuilder(status);
    // }

}