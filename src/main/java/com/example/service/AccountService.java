package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public void register(Account newAccount){
            accountRepository.save(newAccount);
    }

    public Account login(String username, String password) throws AuthenticationException {
        Account account = accountRepository.findByUsernameAndPassword(username, password);
        if(account == null || !account.getPassword().equals(password)){
            return null;
        }
        else{
            return account;
        }
    }

    public boolean findUsername(String username) {
        if(accountRepository.findByUsername(username) != null){
            return true;
        }
        else{
            return false;
        }
    }
}
