package com.example.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.InvalidUserParamException;
import com.example.exception.UnauthorizedLoginException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository r){
        accountRepository = r;
    }

    public Account registerAccount(Account a) throws InvalidUserParamException, DataIntegrityViolationException{
        if (a.getUsername().isBlank() || a.getPassword().length() < 4){
            throw new InvalidUserParamException();
        } 
            return accountRepository.save(a);
    }

    public Account loginAccount(Account a) throws UnauthorizedLoginException {
        
        Account retrievedAccount = accountRepository.findByUsername(a.getUsername());
        if (retrievedAccount != null && retrievedAccount.getPassword() != null && (! retrievedAccount.getPassword().isBlank()) && a.getPassword().equals( retrievedAccount.getPassword())){
            return retrievedAccount;
        } else {
            throw new UnauthorizedLoginException();
        }
        
        

    }
}
