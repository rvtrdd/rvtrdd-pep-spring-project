package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;


public interface AccountRepository extends JpaRepository<Account, Integer>{
    Account findByUsername(String username);
}
