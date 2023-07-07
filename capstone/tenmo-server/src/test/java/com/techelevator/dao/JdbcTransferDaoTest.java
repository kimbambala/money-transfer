package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcTransferDaoTest extends BaseDaoTests {
    protected static final Account ACCOUNT1 = new Account(2050, 1001, BigDecimal.valueOf(10000.00));
    protected static final Account ACCOUNT2 = new Account(2051, 1002, BigDecimal.valueOf(10000));
    private static final Account ACCOUNT3 = new Account(2052, 1003, BigDecimal.valueOf(10000));

    private JdbcAccountDao sut;


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);


    }
}
