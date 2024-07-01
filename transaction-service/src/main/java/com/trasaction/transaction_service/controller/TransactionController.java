package com.trasaction.transaction_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base_domain.base_service.dto.Transaction;
import com.trasaction.transaction_service.service.TransactionService;

@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {
	private TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/create-transaction")
	public ResponseEntity<String> createTransaction(@RequestBody Transaction transaction) {
		return transactionService.createTransaction(transaction);
	}

	@PutMapping("/update-transaction")
	public ResponseEntity<String> updateTransaction(@RequestBody Transaction transaction) {
		return transactionService.updateTransaction(transaction);
	}

	@GetMapping("/get-transaction")
	public ResponseEntity<Transaction> getTransaction(@RequestParam Long transaction_id) {
		return transactionService.getTransactionById(transaction_id);
	}


}
