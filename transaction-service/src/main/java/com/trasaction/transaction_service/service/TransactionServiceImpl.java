package com.trasaction.transaction_service.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.base_domain.base_service.dto.Transaction;
import com.base_domain.base_service.dto.TransactionEvent;
import com.base_domain.base_service.repository.TransactionRepository;
import com.trasaction.transaction_service.kafka.TransactionProducer;

public class TransactionServiceImpl implements TransactionService {

	private TransactionProducer transactionProducer;
	private TransactionRepository transactionRepository;

	public TransactionServiceImpl(TransactionProducer transactionProducer,TransactionRepository transactionRepository) {
		this.transactionProducer = transactionProducer;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public ResponseEntity<String> createTransaction(Transaction transaction) {
		try {
			TransactionEvent transactionEvent = new TransactionEvent();
			transactionEvent.setStatus("IN PROGRESS");
			transactionEvent.setMessage("Transaction is in progress");
			transactionEvent.setTransaction(transaction);
			transactionProducer.senMessage(transactionEvent);
			return ResponseEntity.status(HttpStatus.CREATED).body("Transaction created successfully");
		} catch(Exception exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an issue in the transaction");
		}
	}


	@Override
	public ResponseEntity<String> updateTransaction(Transaction transaction) {
		try {
			TransactionEvent transactionEvent = new TransactionEvent();
			transactionEvent.setStatus("UPDATE IN PROGRESS");
			transactionEvent.setMessage("Transaction update is in progress");
			transactionEvent.setTransaction(transaction);
			transactionProducer.senMessage(transactionEvent);
			return ResponseEntity.status(HttpStatus.OK).body("Transaction updated successfully");
		} catch(Exception exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an issue in the transaction");
		}
	}

	@Override
	public ResponseEntity<Transaction> getTransactionById(Long id) {
		Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
		if (optionalTransaction.isPresent()) {
			return ResponseEntity.ok(optionalTransaction.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
