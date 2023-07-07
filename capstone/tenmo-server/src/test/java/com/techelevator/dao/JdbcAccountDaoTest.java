package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcAccountDaoTest extends BaseDaoTests{
    protected static final Account ACCOUNT1 = new Account(2050, 1001, BigDecimal.valueOf(10000.00));
    protected static final Account ACCOUNT2 = new Account(2051, 1002, BigDecimal.valueOf(10000));
    private static final Account ACCOUNT3 = new Account(2052, 1003, BigDecimal.valueOf(10000));

    private JdbcAccountDao sut;


    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);



    }

    @Test
    public void getBalance_returns_correct_balance(){
        Account account = sut.getAccountByUserId(1001);
        BigDecimal expectedBalance = BigDecimal.valueOf(10000.00);
        BigDecimal testBalance = account.getBalance();
        int expectedBalanceAsInt = expectedBalance.intValue();
        int testBalanceAsInt = testBalance.intValue();
        Assert.assertEquals(expectedBalanceAsInt, testBalanceAsInt);

    }

    @Test
    public void getAccountById_returns_correct_account(){
        //Arrange

        Account account = sut.getAccountById(2050);
        assertAccountsMatch(ACCOUNT1, account);


    }

    @Test
    public void getAccountByUserId_returns_correct_account(){
        Account account = sut.getAccountByUserId(1001);
        assertAccountsMatch(ACCOUNT1, account);

    }

    @Test
    public void depositToAccount_Deposits_Correct_Amount() {
        BigDecimal amountToDeposit = BigDecimal.valueOf(400);
        Account account = sut.depositToAccount(2050, amountToDeposit);
        BigDecimal newBalance = account.getBalance().add(amountToDeposit);
        int newBalanceAsInt = newBalance.intValue();
        Assert.assertEquals(10400, newBalanceAsInt);

    }

    @Test
    public void withdrawFromAccount_Withdraws_Correct_Amount() {
        BigDecimal amountToWithdraw = BigDecimal.valueOf(300);
        Account account = sut.withdrawFromAccount(2050, amountToWithdraw);
        BigDecimal newBalance = account.getBalance().subtract(amountToWithdraw);
        int newBalanceAsInt = newBalance.intValue();
        Assert.assertEquals(9700, newBalanceAsInt);
    }

    private void assertAccountsMatch(Account expected, Account actual){
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        int expectedBalanceAsInt = expected.getBalance().intValue();
        int actualBalanceAsInt = actual.getBalance().intValue();
        Assert.assertEquals(expectedBalanceAsInt, actualBalanceAsInt);
    }


}
