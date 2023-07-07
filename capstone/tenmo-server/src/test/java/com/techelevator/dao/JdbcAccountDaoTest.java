package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcAccountDaoTest extends BaseDaoTests{
    protected static final Account ACCOUNT1 = new Account(2050, 1050, BigDecimal.valueOf(10000));
    protected static final Account ACCOUNT2 = new Account(2051, 1051, BigDecimal.valueOf(10000));
    private static final Account ACCOUNT3 = new Account(2052, 1052, BigDecimal.valueOf(10000));

    private JdbcAccountDao sut;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getBalance_returns_correct_balance(){
        //Arrange
        //Account account = ACCOUNT1;
       // BigDecimal testBalance = sut.getBalance(1050);
        //Assert.assertEquals(BigDecimal.valueOf(10000), testBalance);
        //assertAccountsMatch(ACCOUNT1, testBalance );



        //Act

        //Assert
    }

    @Test
    public void getAccountById_returns_correct_account(){
        //Arrange
        Account account = sut.getAccountById(2050);
        Assert.assertEquals(ACCOUNT1, account);



        //Act

        //Assert
    }

    private void assertAccountsMatch(Account expected, Account actual){
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getBalance(), actual.getBalance());
    }


}
