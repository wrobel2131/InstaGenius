package com.instagenius.notificationservice.infrastructure.adapter;

import com.instagenius.notificationservice.application.EmailSenderPort;
import com.instagenius.notificationservice.domain.OrderEvent;

import com.instagenius.notificationservice.domain.PaymentEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.util.encoders.UTF8;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.codec.CharEncoding.UTF_8;

@Service
@RequiredArgsConstructor
public class EmailSenderAdapter implements EmailSenderPort {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;

    @Async
    @Override
    public void sendOrderRelatedEmail(OrderEvent orderEvent) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8);
            messageHelper.setFrom("contact@instagenius.com");

            final String templateName = "order-notification-email.html";

            Map<String, Object> variables = new HashMap<>();
            variables.put("referenceId", orderEvent.getReferenceId());
            variables.put("userFirstName", orderEvent.getUserFirstName());
            variables.put("userLastName", orderEvent.getUserLastName());
            variables.put("orderStatus", orderEvent.getOrderStatus());
            variables.put("items", orderEvent.getItems());
            variables.put("totalPrice", orderEvent.getTotalPrice());
            variables.put("additionalInfo", orderEvent.getAdditionalInfo());

            Context context = new Context();
            context.setVariables(variables);

            String htmlContent = springTemplateEngine.process(templateName, context);
            messageHelper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

        } catch(MessagingException messagingException) {

        }
    }

    @Override
    public void sendPaymentRelatedEmail(PaymentEvent paymentEvent) {

    }
}
