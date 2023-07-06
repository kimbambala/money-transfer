package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getBalance(int userId);
    Account getAccountById(int accountId);
    Account withdrawFromAccount( int accountId, BigDecimal amount);
    Account getAccountByUserId(int userId);


    Account depositToAccount(int accountId, BigDecimal amount);
}
