package com.notification.notification_service.service;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender javaMailSender;

	@Autowired
	public EmailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	@Override
	public void sendEmail(String from, String to,String subject, String text ) throws MessagingException {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			String[] emailArray = to.split(",\\s*");
			emailArray = Arrays.stream(emailArray).map(String::trim).toArray(String[]::new);
			helper.setTo(emailArray);
			helper.setSubject(subject);
			helper.setText(text, true);
			javaMailSender.send(message);
		} catch(Exception exception) {

		}
	}
}
