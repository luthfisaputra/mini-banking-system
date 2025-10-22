package com.project.minibankingsystem.service;

import com.project.minibankingsystem.model.Account;
import com.project.minibankingsystem.model.Transaction;
import com.project.minibankingsystem.repository.AccountRepository;
import com.project.minibankingsystem.repository.TransactionRepository;

import jakarta.transaction.Transactional;

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

    // Transfer saldo
    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, Double amount) {
        if (fromAccountId.equals(toAccountId)) {
            throw new RuntimeException("Transfer tidak dapat dilakukan ke akun yang sama");
        }

        Account fromAcc = accountRepo.findById(fromAccountId)
            .orElseThrow(() -> new RuntimeException("Akun pengirim tidak ditemukan"));
        Account toAcc = accountRepo.findById(toAccountId)
            .orElseThrow(() -> new RuntimeException("Akun penerima tidak ditemukan"));

        if (fromAcc.getBalance() < amount) {
            throw new RuntimeException("Saldo tidak mencukupi");
        }

        // kurangi saldo pengirim
        fromAcc.setBalance(fromAcc.getBalance() - amount);
        accountRepo.save(fromAcc);

        // tambahkan saldo penerima
        toAcc.setBalance(toAcc.getBalance() + amount);
        accountRepo.save(toAcc);

        // Catat transaksi pengirim
        Transaction fromTrans = new Transaction();
        fromTrans.setType("Transfer");
        fromTrans.setAmount(amount);
        fromTrans.setTimestamp(LocalDateTime.now());
        fromTrans.setAccount(fromAcc);
        transactionRepo.save(fromTrans);

        // Catat transaksi penerima
        Transaction toTrans = new Transaction();
        toTrans.setType("Transfer");
        toTrans.setAmount(amount);
        toTrans.setTimestamp(LocalDateTime.now());
        toTrans.setAccount(toAcc);
        transactionRepo.save(toTrans);
    }
}
