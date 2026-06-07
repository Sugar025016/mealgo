package com.mealgo.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mealgo.entity.User;
import com.mealgo.service.IEmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final JavaMailSender javaMailSender;

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${spring.mail.username}")
    private String mealgoEmail;

    @Async
    @Override
    public void sendVerifyEmail(User user, String token) {

        String verifyUrl = baseUrl + "/auth/verify?token="
                + token;
        String html = """
                <html>
                <body style="font-family: Arial, sans-serif;">
                    <h1>MealGo 電子郵件驗證</h1>

                    <p>謝謝您註冊 MealGo！</p>

                    <a href="%s">
                        驗證信箱
                    </a>

                    <p>如果您沒有註冊，可以忽略此信件。</p>
                </body>
                </html>
                """.formatted(verifyUrl);

        try {

            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(mealgoEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("MealGo 信箱驗證");
            helper.setText(html, true);

            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}