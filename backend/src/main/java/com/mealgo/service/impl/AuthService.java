package com.mealgo.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mealgo.dto.request.LoginRequest;
import com.mealgo.dto.response.LoginResponse;
import com.mealgo.entity.User;
import com.mealgo.repository.IUserRepository;
import com.mealgo.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("帳號或密碼錯誤"));
        // System.out.println("user.getPassword() = " + user.getPassword());
        // System.out.println("request.getPassword() = " + request.getPassword());
        // System.out.println(new BCryptPasswordEncoder().encode("123456"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("帳號或密碼錯誤");
        }

        String token = jwtUtil.generateToken(user);

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                token);
    }
}