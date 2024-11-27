package com.instagenius.notificationservice.infrastructure.adapter;

import com.instagenius.notificationservice.application.EmailSenderPort;
import com.instagenius.notificationservice.domain.OrderEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailSenderAdapter implements EmailSenderPort {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;

    @Override
    public void sendOrderRelatedEmail(OrderEvent orderEvent) {

    }
}
