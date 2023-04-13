package com.bank.accountsapi.service.impl;

import com.bank.accountsapi.model.Account;
import com.bank.accountsapi.repository.AccountRepository;
import com.bank.accountsapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ResponseEntity<Object> findByAccountId(String accountId) {
        Optional<Account> accountEntityOpt = getAccountById(accountId);
        if(accountEntityOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(accountEntityOpt);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account id " + accountId + " not found.");
        }
    }

    @Override
    public ResponseEntity<Object> addNewAccount(Account account) {
        Optional<Account> accountEntityOpt = accountRepository.findById(account.getId());
        if(getAccountById(account.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account id " + account.getId() + " already exists.");
        } else {
            saveAccount(account);
            return ResponseEntity.status(HttpStatus.OK).body("New Account created successfully.");
        }
    }

    @Override
    public ResponseEntity<Object> updateBalance(String accountId, Double balance) {
        Optional<Account> accountEntityOpt = getAccountById(accountId);
        if(accountEntityOpt.isPresent()) {
            Account account = accountEntityOpt.get();
            account.setBalance(account.getBalance() + balance);
            saveAccount(account);
            return ResponseEntity.status(HttpStatus.OK).body("Money added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account id " + accountId + " not found.");
        }
    }

    @Override
    public ResponseEntity<Object> deleteAccount(String accountId) {
        if(getAccountById(accountId).isPresent()) {
            accountRepository.deleteById(accountId);
            return ResponseEntity.status(HttpStatus.OK).body("Account deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account id " + accountId + " not found.");
        }
    }

    @Override
    public Optional<Account> getAccountById(String accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public void saveAccount(Account account){
        accountRepository.save(account);
    }

}
