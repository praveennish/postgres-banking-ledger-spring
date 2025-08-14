package com.example.bankingledger.repository;

import com.example.bankingledger.model.Transaction;
import com.example.bankingledger.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t where t.txnType = :type AND t.amount > :amount AND t.txnDate >= :fromDate")
    List<Transaction> findSuspicious(@Param("type") TransactionType type,
                                     @Param("amount") BigDecimal amount,
                                     @Param("fromDate") LocalDateTime fromDate);
}
