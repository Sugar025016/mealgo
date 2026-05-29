package com.mealgo.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mealgo.dto.request.PasswordRequest;
import com.mealgo.dto.request.UserCreateRequest;
import com.mealgo.dto.request.UserUpdateRequest;
import com.mealgo.dto.response.UserResponse;
import com.mealgo.entity.User;
import com.mealgo.exception.ResourceNotFoundException;
import com.mealgo.repository.IUserRepository;
import com.mealgo.service.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .toList();
    }

    @Override
    public UserResponse findById(Integer id) {

        User user = getUser(id);

        return new UserResponse(user);
    }

    @Override
    public UserResponse create(UserCreateRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return new UserResponse(
                userRepository.save(user));
    }

    @Override
    public UserResponse update(Integer id, UserUpdateRequest request) {

        User user = getUser(id);

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());

        return new UserResponse(
                userRepository.save(user));
    }

    @Override
    public void delete(Integer id) {

        User user = getUser(id);

        userRepository.delete(user);
    }

    private User getUser(Integer id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "使用者"));
    }

    @Override
    public void updatePassword(
            Integer userId,
            PasswordRequest request) {

        User user = getUser(userId);

        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword())) {

            throw new RuntimeException("舊密碼錯誤");
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()));

        userRepository.save(user);
    }

}