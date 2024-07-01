package com.trasaction.transaction_service.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.base_domain.base_service.dto.Transaction;
import com.base_domain.base_service.dto.TransactionEvent;
import com.base_domain.base_service.repository.TransactionRepository;
import com.trasaction.transaction_service.kafka.TransactionProducer;

class TransactionServiceTest {

    @Mock
    private TransactionProducer transactionProducer;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction;
    private TransactionEvent transactionEvent;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(100.0);
        transaction.setFromAccountId(12345678902L);
        transaction.setToAccountId(12345678903L);
        transaction.setTimestamp(LocalDateTime.now());
        transactionEvent = new TransactionEvent();
        transactionEvent.setStatus("IN PROGRESS");
        transactionEvent.setMessage("Transaction is in progress");
        transactionEvent.setTransaction(transaction);
    }

    @Test
    void testCreateTransaction() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Transaction created successfully", HttpStatus.CREATED);
        doNothing().when(transactionProducer).senMessage(any(TransactionEvent.class));
        ResponseEntity<String> result = transactionService.createTransaction(transaction);
        assertEquals(responseEntity.getStatusCode(), result.getStatusCode());
        assertEquals(responseEntity.getBody(), result.getBody());
        verify(transactionProducer, times(1)).senMessage(any(TransactionEvent.class));
    }

    @Test
    void testUpdateTransaction() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Transaction updated successfully", HttpStatus.OK);
        doNothing().when(transactionProducer).senMessage(any(TransactionEvent.class));
        ResponseEntity<String> result = transactionService.updateTransaction(transaction);
        assertEquals(responseEntity.getStatusCode(), result.getStatusCode());
        assertEquals(responseEntity.getBody(), result.getBody());
        verify(transactionProducer, times(1)).senMessage(any(TransactionEvent.class));
    }

    @Test
    void testGetTransactionById_Found() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));
        ResponseEntity<Transaction> result = transactionService.getTransactionById(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(transaction, result.getBody());
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTransactionById_NotFound() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Transaction> result = transactionService.getTransactionById(1L);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
        verify(transactionRepository, times(1)).findById(1L);
    }
}
