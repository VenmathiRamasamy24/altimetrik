package com.notification.notification_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.base_domain.base_service.dto.TransactionEvent;
import com.notification.notification_service.service.EmailService;

@Service
public class NotificationConsumer {

	private EmailService emailService;

	public NotificationConsumer(EmailService emailService) {
		this.emailService = emailService;
	}
	@KafkaListener(topics = "${spring.kafka.topic.name}",
			groupId = "${spring.kafka.consumer.group.name}")
	public void consume(TransactionEvent transactionEvent) {
		emailService.sendEmail("venmathi24@gmail.com", "venmathi24@gmail.com", "Reg: Tranaction", "Transaction Intialized for"+ transactionEvent.getTransaction().getAmount() );
	}
}
