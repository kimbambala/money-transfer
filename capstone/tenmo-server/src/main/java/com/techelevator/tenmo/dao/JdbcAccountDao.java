package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public double getBalance(int userId) {
        double balance = 0;

        String sql = "SELECT balance FROM account WHERE user_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            balance = mapRowToAccount(results).getBalance();
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server / database");
        }
        return balance;
    }

    @Override
    public Account getAccountByUserId(int userId){
        Account account = null;

        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            account = mapRowToAccount(results);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server / database");
        }

        return account;

    }

    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getDouble("balance"));
        return account;
    }
}
