package com.bank.accountsapi.model.domain;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Receiver extends Payment {
    public String fromAccount;
}
