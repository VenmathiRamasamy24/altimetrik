package com.trasaction.transaction_service.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.base_domain.base_service.dto.Transaction;

@Service
public interface TransactionService {
	ResponseEntity<String> createTransaction(Transaction transaction);
	ResponseEntity<String> updateTransaction(Transaction transaction);
	ResponseEntity<Transaction> getTransactionById(Long id);
}
