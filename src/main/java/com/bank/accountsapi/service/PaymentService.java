package com.bank.accountsapi.service;

import com.bank.accountsapi.model.Transaction;
import org.springframework.http.ResponseEntity;

public interface PaymentService {

    /**
     * GET payment transfer made by account
     *
     * @param accountId
     * @return all list of payment transfers in which accountId exists wrapped under ResponseEntity
     */
    ResponseEntity<Object> getPaymentsByAccountId(String accountId);

    /**
     * GET payment transfer made by account
     *
     * @return all list of payment transfers wrapped under ResponseEntity
     */
    ResponseEntity<Object> getPayments();

    /**
     * POST payment transfer made by account
     *
     * @param transaction
     * @return success or failure message wrapped under ResponseEntity
     */
    ResponseEntity<Object> transferMoney(Transaction transaction);

    /**
     * POST save transaction
     *
     * @param transaction
     * Is used only for internal calls
     */
    void savePayment(Transaction transaction);
}
