package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransactionRequest;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/account/{accountId}", method = RequestMethod.GET)
    public Account getAccountById(@PathVariable int accountId) {
        Account account = accountDao.getAccountById(accountId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        } else {
            return account;
        }

    }

   // @RequestMapping(path = "/account/withdraw/{accountFrom}", method = RequestMethod.PUT)
//    public Account withdrawFromAccount(@RequestBody Transfer transfer, @PathVariable int accountFrom) {
//        account.setAccountId(accountId);
//        try {
//            Account updatedAccount = accountDao.withdrawFromAccount(accountFrom, transfer.getAmount());
//            transfer.
//            return updatedAccount;
//        } catch (DaoException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found.");
//        }
//        return updatedAccount;

        @RequestMapping(path = "/account/{accountId}/withdraw/{amount}", method = RequestMethod.PUT)
        public Account withdrawFromAccount(@PathVariable int accountId, @PathVariable BigDecimal amount) {
            int currentUserAccountId = accountDao.getAccountById(accountId).getAccountId();

            Account account = accountDao.withdrawFromAccount(currentUserAccountId, amount );

            return account;
    }

    @RequestMapping(path = "/account/{accountId}/deposit/{amount}", method = RequestMethod.PUT)
    public Account depositToAccount(@PathVariable int accountId, @PathVariable BigDecimal amount) {
        int currentUserAccountId = accountDao.getAccountById(accountId).getAccountId();
        Account account = accountDao.depositToAccount(currentUserAccountId, amount);

        return account;
    }


    @RequestMapping(path = "/account/user/{userId}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable int userId) {
        Account account = accountDao.getAccountByUserId(userId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        } else {
            return account;
        }

    }



}
