package com.example.bankingledger.controller;

import com.example.bankingledger.dto.TransactionRequest;
import com.example.bankingledger.model.Account;
import com.example.bankingledger.model.Transaction;
import com.example.bankingledger.model.TransactionType;
import com.example.bankingledger.repository.AccountRepository;
import com.example.bankingledger.repository.TransactionRepository;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionController(TransactionRepository transactionRepository, AccountRepository accountRepository){
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest req){
        Optional<Account> acctOpt = accountRepository.findById(req.getAccountId());
        if(acctOpt.isEmpty())
            return ResponseEntity.badRequest().body("Account not found");

        Account account = acctOpt.get();

        Transaction t = new Transaction();
        t.setAccount(account);
        t.setAmount(req.getAmount());
        t.setTxnType(TransactionType.valueOf(req.getTxnType()));
        t.setDescription(req.getDescription());
        t.setTxnDate(LocalDateTime.now());

        if(t.getTxnType().equals(TransactionType.WITHDRAWAL)){
            account.setBalance(account.getBalance().subtract(t.getAmount()));
        }
        else if(t.getTxnType().equals(TransactionType.DEPOSIT)){
            account.setBalance(account.getBalance().add(t.getAmount()));
        }

        accountRepository.save(account);
        Transaction saved = transactionRepository.save(t);

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/suspicious")
    public List<Transaction> getSuspicious(@RequestParam BigDecimal amount, @RequestParam int days){
        return transactionRepository.findSuspicious(TransactionType.WITHDRAWAL, amount, LocalDateTime.now().minusDays(days));
    }
}
