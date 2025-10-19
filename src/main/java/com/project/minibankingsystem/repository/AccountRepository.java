package com.project.minibankingsystem.repository;

import com.project.minibankingsystem.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // Contoh query custom: cari akun berdasarkan nama
    Account findByName(String name);
}

