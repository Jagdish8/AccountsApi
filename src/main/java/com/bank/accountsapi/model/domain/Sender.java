package com.bank.accountsapi.model.domain;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Sender extends Payment {
    public String toAccount;
}
