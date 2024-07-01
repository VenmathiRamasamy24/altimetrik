package com.notification.notification_service.service;

import org.springframework.messaging.MessagingException;

public interface EmailService {
    void sendEmail(String from, String to,String subject, String text) throws MessagingException;
}
