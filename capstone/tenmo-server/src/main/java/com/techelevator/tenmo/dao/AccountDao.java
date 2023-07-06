package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    public BigDecimal getBalance(int userId);
    public Account getAccountById(int accountId);
    public Account withdrawFromAccount( int accountId, BigDecimal amount);
    public Account getAccountByUserId(int userId);


    void depositToAccount(int accountId, BigDecimal amount);
}
