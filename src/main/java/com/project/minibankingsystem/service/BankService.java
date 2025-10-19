package com.project.minibankingsystem.service;

import com.project.minibankingsystem.model.Account;
import com.project.minibankingsystem.model.Transaction;
import com.project.minibankingsystem.repository.AccountRepository;
import com.project.minibankingsystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BankService {
    
    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    // Registrasi akun baru
    public Account createAccount(String name, Double initialBalance) {
        Account acc = new Account();
        acc.setName(name);
        acc.setBalance(initialBalance);
        return accountRepo.save(acc);
    }

    // Deposit Saldo
    public void deposit(Long accountId, Double amount) {
        Account acc = accountRepo.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Akun tidak ditemukan"));
        acc.setBalance(acc.getBalance() + amount);
        accountRepo.save(acc);
        
        Transaction trans = new Transaction();
        trans.setType("Deposit");
        trans.setAmount(amount);
        trans.setTimestamp(LocalDateTime.now());
        trans.setAccount(acc);
        transactionRepo.save(trans);
    }

    // Withdraw saldo
    public void withdraw(Long accountId, Double amount) {
        Account acc = accountRepo.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Akun tidak ditemukan"));
        if (acc.getBalance() < amount) {
            throw new RuntimeException("Saldo tidak mencukupi");
        }
        acc.setBalance(acc.getBalance() - amount);
        accountRepo.save(acc);
        
        Transaction trans = new Transaction();
        trans.setType("Withdraw");
        trans.setAmount(amount);
        trans.setTimestamp(LocalDateTime.now());
        trans.setAccount(acc);
        transactionRepo.save(trans);
    }

    // Cek saldo
    public Double getBalance(Long accountId) {
        Account acc = accountRepo.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Akun tidak ditemukan"));
        return acc.getBalance();
    }

    // Ambil histori transaksi
    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepo.findByAccountIdOrderByTimestampDesc(accountId);
    }
}
