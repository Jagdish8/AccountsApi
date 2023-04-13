package com.bank.accountsapi.service;

import com.bank.accountsapi.model.Account;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface AccountService {
    /**
     * GET account by accountId
     *
     * @param accountId
     * @return account wrapped under ResponseEntity
     */
    ResponseEntity<Object> findByAccountId(String accountId);

    /**
     * POST create new account
     *
     * @param account
     * @return success or failure message wrapped under ResponseEntity
     */
    ResponseEntity<Object> addNewAccount(Account account);

    /**
     * PUT add or withdraw money from account
     *
     * @param accountId
     * @param balance
     * @return success or failure message wrapped under ResponseEntity
     */
    ResponseEntity<Object> updateBalance(String accountId, Double balance);

    /**
     * DELETE acount
     *
     * @param accountId
     * @return success or failure message wrapped under ResponseEntity
     */
    ResponseEntity<Object> deleteAccount(String accountId);

    /**
     * GET account by accountId
     *
     * @param accountId
     * @return Optional<Account>, Is used only for internal calls
     */
    Optional<Account> getAccountById(String accountId);

    /**
     * POST save account
     *
     * @param account
     * Is used only for internal calls
     */
    void saveAccount(Account account);

}
