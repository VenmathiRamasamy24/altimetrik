package com.notification.notification_service.kafka;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.base_domain.base_service.dto.Transaction;
import com.base_domain.base_service.dto.TransactionEvent;
import com.notification.notification_service.service.EmailService;

public class NotificationConsumerTest {

	@Mock
	private EmailService emailService;
	@InjectMocks
	private NotificationConsumer notificationConsumer;
	private TransactionEvent transactionEvent;
	private Transaction transaction;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		transaction = new Transaction();
		transaction.setId(1L);
		transaction.setAmount(1000.0);
		transaction.setFromAccountId(12367733883883L);
		transaction.setToAccountId(456393982332983L);
		transaction.setTimestamp(LocalDateTime.now());
		transactionEvent = new TransactionEvent("Transaction is in progress","IN PROGRESS", transaction);
	}

	@Test
	public void testConsume() {
		notificationConsumer.consume(transactionEvent);
		verify(emailService, times(1)).sendEmail(
				"venmathi24@gmail.com",
				"venmathi24@gmail.com",
				"Reg: Tranaction",
				"Transaction Intialized for" + transaction.getAmount()
				);
	}
}
