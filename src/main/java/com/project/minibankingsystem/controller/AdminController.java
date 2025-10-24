package com.project.minibankingsystem.controller;

import com.project.minibankingsystem.model.Account;
import com.project.minibankingsystem.repository.AccountRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private AccountRepository accountRepo;

    @GetMapping("/accounts")
    public Iterable<Account> getAccounts() {
        return accountRepo.findAll();
    }
}
