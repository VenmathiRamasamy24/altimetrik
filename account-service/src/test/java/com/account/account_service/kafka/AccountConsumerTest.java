package com.account.account_service.kafka;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.base_domain.base_service.dto.Transaction;
import com.base_domain.base_service.dto.TransactionEvent;
import com.base_domain.base_service.repository.TransactionRepository;

public class AccountConsumerTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountConsumer accountConsumer;

    private TransactionEvent transactionEventInProgress;
    private TransactionEvent transactionEventUpdateInProgress;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(100.0);
        transaction.setFromAccountId(12345678902L);
        transaction.setToAccountId(12345678903L);
        transaction.setTimestamp(LocalDateTime.now());
        transactionEventInProgress = new TransactionEvent("transaction is in progress", "IN PROGRESS", transaction);
        transactionEventUpdateInProgress = new TransactionEvent("transaction update is in progress","UPDATE IN PROGRESS", transaction);
    }

    @Test
    public void testConsumeInProgress() {
        accountConsumer.consume(transactionEventInProgress);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    public void testConsumeUpdateInProgress() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));
        accountConsumer.consume(transactionEventUpdateInProgress);
        verify(transactionRepository, times(1)).findById(transaction.getId());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    public void testConsumeUpdateInProgress_NoTransactionFound() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());
        accountConsumer.consume(transactionEventUpdateInProgress);
        verify(transactionRepository, times(1)).findById(transaction.getId());
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }
}
