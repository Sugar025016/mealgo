package com.mealgo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mealgo.dto.request.PasswordRequest;
import com.mealgo.dto.request.UserUpdateRequest;
import com.mealgo.dto.response.UserResponse;
import com.mealgo.entity.User;
import com.mealgo.exception.BadRequestException;
import com.mealgo.exception.ResourceNotFoundException;
import com.mealgo.repository.IUserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(1);
        user.setName("測試使用者");
        user.setPhone("0911111111");
        user.setEmail("user@test.com");
        user.setPassword("encodedOldPassword");
        user.setRole("USER");
    }

    @Test
    void findById_success() {

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        UserResponse response = userService.findById(1);

        assertEquals(1, response.getId());
        assertEquals("測試使用者", response.getName());
        assertEquals("user@test.com", response.getEmail());
    }

    @Test
    void findById_userNotFound_throwResourceNotFoundException() {

        when(userRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.findById(99));
    }

    @Test
    void update_success() {

        UserUpdateRequest request = new UserUpdateRequest();
        request.setName("新名稱");
        request.setPhone("0922222222");
        request.setEmail("new@test.com");

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = userService.update(1, request);

        assertEquals("新名稱", response.getName());
        assertEquals("0922222222", response.getPhone());
        assertEquals("new@test.com", response.getEmail());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void updatePassword_success() {

        PasswordRequest request = new PasswordRequest();
        request.setOldPassword("123456");
        request.setNewPassword("654321");

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "123456",
                "encodedOldPassword"))
                .thenReturn(true);

        when(passwordEncoder.encode("654321"))
                .thenReturn("encodedNewPassword");

        userService.updatePassword(1, request);

        assertEquals("encodedNewPassword", user.getPassword());

        verify(userRepository).save(user);
    }

    @Test
    void updatePassword_oldPasswordWrong_throwBadRequestException() {

        PasswordRequest request = new PasswordRequest();
        request.setOldPassword("wrong");
        request.setNewPassword("654321");

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "wrong",
                "encodedOldPassword"))
                .thenReturn(false);

        assertThrows(
                BadRequestException.class,
                () -> userService.updatePassword(1, request));
    }
}