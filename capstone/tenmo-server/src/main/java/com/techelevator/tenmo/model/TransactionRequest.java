package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransactionRequest {

    private BigDecimal amount;
    private int accountId;

    public TransactionRequest() {}

    public TransactionRequest(BigDecimal amount, int accountId) {
        this.amount = amount;
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}

