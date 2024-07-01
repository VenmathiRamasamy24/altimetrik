package com.notification.notification_service.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import jakarta.mail.internet.MimeMessage;

public class EmailServiceTest {

	@Mock
	private JavaMailSender javaMailSender;

	@InjectMocks
	private EmailServiceImpl emailService;

	private MimeMessage mimeMessage;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mimeMessage = mock(MimeMessage.class);
		when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
	}

	@Test
	public void testSendEmail() throws Exception {
		String from = "venmathi24@gmail.com";
		String to = "venmathi24@gmail.com";
		String subject = "Reg: Tranaction";
		String text = "Transaction Intialized for 123";
		emailService.sendEmail(from, to, subject, text);
		verify(javaMailSender, times(1)).createMimeMessage();
		verify(javaMailSender, times(1)).send(mimeMessage);
	}
}
