package com.base_domain.base_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base_domain.base_service.dto.Transaction;

	public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	}
