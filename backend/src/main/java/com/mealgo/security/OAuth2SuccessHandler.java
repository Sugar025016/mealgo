package com.mealgo.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mealgo.dto.ApiResponse;
import com.mealgo.dto.response.LoginResponse;
import com.mealgo.entity.User;
// import com.mealgo.enums.Role;
import com.mealgo.repository.IUserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final IUserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setName(name);
                    // newUser.setPassword();
                    newUser.setRole("USER");
                    return userRepository.save(newUser);
                });

        String token = jwtUtil.generateToken(user);

        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        LoginResponse loginResponse = new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                token);

        ApiResponse<LoginResponse> apiResponse = ApiResponse.success("登入成功", loginResponse);

        new ObjectMapper().writeValue(response.getWriter(), apiResponse);

    }
}