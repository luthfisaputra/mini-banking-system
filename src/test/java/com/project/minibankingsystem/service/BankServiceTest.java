package com.project.minibankingsystem.service;

import com.project.minibankingsystem.model.Account;
import com.project.minibankingsystem.repository.AccountRepository;
import com.project.minibankingsystem.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BankServiceTest {
    
    @Autowired
    private BankService bankService;

    @Autowired
    private AccountRepository accountRepo;

    @Test
    void testCreateAccountAndDeposit() {
        Account acc = bankService.createAccount("Luthfi",  1000.0);
        assertNotNull(acc.getId(), "ID tidak boleh null");

        bankService.deposit(acc.getId(), 500.0);
        Double balance = bankService.getBalance(acc.getId());

        assertEquals(1500.0, balance);
    }

    @Test
    void testWithdraw() {
        Account acc = bankService.createAccount("TestUser",  1000.0);
        bankService.withdraw(acc.getId(), 200.0);
        Double balance = bankService.getBalance(acc.getId());

        assertEquals(800.0, balance);
    }
}
