package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.dto.request.UserRequest;
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
    public UserResponse create(UserRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return new UserResponse(
                userRepository.save(user));
    }

    @Override
    public UserResponse update(Integer id, UserRequest request) {

        User user = getUser(id);

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());

        if (request.getPassword() != null &&
                !request.getPassword().isBlank()) {

            user.setPassword(request.getPassword());
        }

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
}