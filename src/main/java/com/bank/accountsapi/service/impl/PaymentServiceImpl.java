package com.bank.accountsapi.service.impl;

import com.bank.accountsapi.model.Account;
import com.bank.accountsapi.model.Transaction;
import com.bank.accountsapi.model.domain.Direction;
import com.bank.accountsapi.model.domain.Payment;
import com.bank.accountsapi.model.domain.Receiver;
import com.bank.accountsapi.model.domain.Sender;
import com.bank.accountsapi.repository.PaymentRepository;
import com.bank.accountsapi.service.AccountService;
import com.bank.accountsapi.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public ResponseEntity<Object> getPaymentsByAccountId(String accountId) {
        if(accountService.getAccountById(accountId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account id " + accountId + " not found.");
        }
        List<Payment> allPayments = new ArrayList<>();
        List<Transaction> allTransactions = paymentRepository.findAll();
        allTransactions.stream()
                .filter(eachTransaction -> eachTransaction.getToAccount().equals(accountId) ||
                        eachTransaction.getFromAccount().equals(accountId))
                .forEach(eachTransaction -> {
                    if (eachTransaction.getFromAccount().equals(accountId)) {
                        allPayments.add(senderBuilder(eachTransaction));
                    } else {
                        allPayments.add(receiverBuilder(eachTransaction));
                    }
                });
        return ResponseEntity.status(HttpStatus.OK).body(allPayments);
    }

    @Override
    public ResponseEntity<Object> getPayments() {
        List<Payment> allPayments = new ArrayList<>();
        List<Transaction> allTransactions = paymentRepository.findAll();
        for (Transaction eachTransaction : allTransactions) {
            allPayments.add(senderBuilder(eachTransaction));
            allPayments.add(receiverBuilder(eachTransaction));
        }
        return ResponseEntity.status(HttpStatus.OK).body(allPayments);
    }

    @Override
    public ResponseEntity<Object> transferMoney(Transaction transaction) {
        if(transaction.getFromAccount().equals(transaction.getToAccount())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both accounts cannot be same");
        }
        Optional<Account> sender = accountService.getAccountById(transaction.getFromAccount());
        Optional<Account> receiver = accountService.getAccountById(transaction.getToAccount());
        if(sender.isPresent() && receiver.isPresent()) {
            if(sender.get().getBalance() < transaction.getAmount()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient Balance in "+
                        transaction.getFromAccount() +" account");
            }
            if(! Objects.equals(sender.get().getCurrency(), receiver.get().getCurrency())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Currency do not match");
            }
            sender.get().setBalance(sender.get().getBalance() - transaction.getAmount());
            accountService.saveAccount(sender.get());
            receiver.get().setBalance(receiver.get().getBalance() + transaction.getAmount());
            accountService.saveAccount(receiver.get());
            savePayment(transaction);
            return ResponseEntity.status(HttpStatus.OK).body("Money transferred successfully");
        } else {
            return checkWhoExists(sender, transaction);
        }
    }

    private ResponseEntity<Object> checkWhoExists(Optional<Account> sender, Transaction transaction){
        if(sender.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receiver " + transaction.getToAccount() +
                    " not found.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sender " + transaction.getFromAccount() +
                    " not found.");
        }
    }

    @Override
    public void savePayment(Transaction transaction) {
        paymentRepository.save(transaction);
    }

    private Sender senderBuilder(Transaction transaction) {
        return Sender.builder()
                .account(transaction.getFromAccount())
                .amount(transaction.getAmount())
                .toAccount(transaction.getToAccount())
                .direction(Direction.OUTGOING)
                .build();
    }

    private Receiver receiverBuilder(Transaction transaction) {
        return Receiver.builder()
                .account(transaction.getToAccount())
                .amount(transaction.getAmount())
                .fromAccount(transaction.getFromAccount())
                .direction(Direction.INCOMING)
                .build();
    }

}
