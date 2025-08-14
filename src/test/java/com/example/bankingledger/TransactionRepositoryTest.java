package com.example.bankingledger;

import com.example.bankingledger.model.Transaction;
import com.example.bankingledger.model.TransactionType;
import com.example.bankingledger.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgisContainerProvider;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
public class TransactionRepositoryTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("banking_db")
            .withUsername("banking_user")
            .withPassword("banking_pass");

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    void contextLoads(){
        assertNotNull(transactionRepository);
    }

    @Test
    void shouldFindSuspiciousWithdrawals(){
        List<Transaction> results = transactionRepository.findSuspicious(TransactionType.WITHDRAWAL, BigDecimal.valueOf(500), LocalDateTime.now().minusDays(30));
        assertNotEquals( 0,results.size());
    }
}
