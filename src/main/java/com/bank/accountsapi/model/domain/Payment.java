package com.bank.accountsapi.model.domain;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Payment {

    public String account;

    public Double amount;

    public Direction direction;

}
