package com.trasaction.transaction_service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.base_domain.base_service.dto.Transaction;
import com.trasaction.transaction_service.service.TransactionService;

class TransactionControllerTest {

	
	@InjectMocks
	private TransactionController transactionController;
	
	@Mock
	private TransactionService transactionService;
	
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	public Transaction getTransaction() {
		Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(100.0);
        transaction.setFromAccountId(12345678902L);
        transaction.setToAccountId(12345678903L);
        transaction.setTimestamp(LocalDateTime.now());
        return transaction;
	}
	
	
	@Test
    void testCreateTransaction() {
        Transaction transaction = getTransaction();
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Transaction created", HttpStatus.OK);
        when(transactionService.createTransaction(transaction)).thenReturn(responseEntity);
        ResponseEntity<String> result = transactionController.createTransaction(transaction);
        assertEquals(responseEntity, result);
        verify(transactionService, times(1)).createTransaction(transaction);
    }
	
	@Test
    void testUpdateTransaction() {
        Transaction transaction = getTransaction();
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Transaction update", HttpStatus.OK);
        when(transactionService.updateTransaction(transaction)).thenReturn(responseEntity);
        ResponseEntity<String> result = transactionController.updateTransaction(transaction);
        assertEquals(responseEntity, result);
        verify(transactionService, times(1)).updateTransaction(transaction);
    }
	
	@Test
    void testGetTransaction() {
        ResponseEntity<Transaction> responseEntity = new ResponseEntity<>(getTransaction(), HttpStatus.OK);
        when(transactionService.getTransactionById(anyLong())).thenReturn(responseEntity);
        ResponseEntity<Transaction> result = transactionController.getTransaction(anyLong());
        assertEquals(responseEntity, result);
        verify(transactionService, times(1)).getTransactionById(anyLong());
    }

}
