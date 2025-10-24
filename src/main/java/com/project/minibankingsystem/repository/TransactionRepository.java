package com.project.minibankingsystem.repository;

import com.project.minibankingsystem.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountIdOrderByTimestampDesc(Long accountId);

    List<Transaction> findByAccountIdAndTimestampBetween(Long accountId, LocalDateTime from, LocalDateTime to);
}
