package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

public interface AccountDao {

    public double getBalance(int userId);
    public Account getAccountById(int accountId);
    
    public Account getAccountByUserId(int userId);
    
    
}
