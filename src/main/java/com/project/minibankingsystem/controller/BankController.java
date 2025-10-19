package com.project.minibankingsystem.controller;

import com.project.minibankingsystem.model.Account;
import com.project.minibankingsystem.model.Transaction;
import com.project.minibankingsystem.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class BankController {

    @Autowired
    private BankService bankService;

    // Endpoint membuat akun baru
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return bankService.createAccount(account.getName(), account.getBalance());
    }

    // Endpoint deposit saldo
    @PostMapping("/{Id}/deposit")
    public String deposit(@PathVariable Long Id, @RequestParam Double amount) {
        bankService.deposit(Id, amount);
        return "Deposit berhasil ke akun " + Id;
    }

    // Endpoint withdraw saldo
    @PostMapping("/{Id}/withdraw")
    public String withdraw(@PathVariable Long Id, @RequestParam Double amount) {
        bankService.withdraw(Id, amount);
        return "Withdraw berhasil ke akun " + Id;
    }

    // Endpoint cek saldo
    @GetMapping("/{Id}/balance")
    public Double getBalance(@PathVariable Long Id) {
        return bankService.getBalance(Id);
    }

    // Endpoint ambil histori transaksi
    @GetMapping("/{Id}/transactions")
    public List<Transaction> getTransactions(@PathVariable Long Id) {
        return bankService.getTransactions(Id);
    }
}
