package com.mealgo.service;

import com.mealgo.entity.User;

public interface IEmailService {

    void sendVerifyEmail(User user, String token);
}
