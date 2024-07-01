package com.trasaction.transaction_service.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.base_domain.base_service.dto.TransactionEvent;

@Service
public class TransactionProducer {

	private NewTopic newTopic;
	private KafkaTemplate<NewTopic, TransactionEvent> kafkaTemplate;
	public TransactionProducer(NewTopic newTopic, KafkaTemplate<NewTopic, TransactionEvent> kafkaTemplate) {
		super();
		this.newTopic = newTopic;
		this.kafkaTemplate = kafkaTemplate;
	}

	public void senMessage(TransactionEvent event) {
		Message<TransactionEvent> message = MessageBuilder
				.withPayload(event)
				.setHeader(KafkaHeaders.TOPIC, newTopic.name())
				.build();
		kafkaTemplate.send(message);
	}

}
