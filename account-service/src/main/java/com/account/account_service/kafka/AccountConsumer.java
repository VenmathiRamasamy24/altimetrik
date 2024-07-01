package com.account.account_service.kafka;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.base_domain.base_service.dto.Transaction;
import com.base_domain.base_service.dto.TransactionEvent;
import com.base_domain.base_service.repository.TransactionRepository;

@Service
public class AccountConsumer {

	private final TransactionRepository transactionRepository;

	@Autowired
	public AccountConsumer(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group.name}")
	public void consume(TransactionEvent transactionEvent) {
		if (transactionEvent.getStatus().equalsIgnoreCase("IN PROGRESS")) {
			transactionRepository.save(transactionEvent.getTransaction());
		} else if (transactionEvent.getStatus().equalsIgnoreCase("UPDATE IN PROGRESS")) {
			Transaction currentTransaction = transactionEvent.getTransaction();
			Optional<Transaction> optionalTransaction = transactionRepository.findById(currentTransaction.getId());
			if (optionalTransaction.isPresent()) {
				Transaction transaction = optionalTransaction.get();
				transaction.setAmount(currentTransaction.getAmount());
				transaction.setFromAccountId(currentTransaction.getFromAccountId());
				transaction.setToAccountId(currentTransaction.getToAccountId());
				transaction.setTimestamp(currentTransaction.getTimestamp());
				transactionRepository.save(transaction);
			}
		}
	}
}
