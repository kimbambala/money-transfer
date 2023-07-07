package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDaoTest extends BaseDaoTests {
   public static final Transfer TRANSFER1 = new Transfer(3001,2,2,2050,2051, new BigDecimal("75.00"));
   public static final Transfer TRANSFER2 = new Transfer(3002,1,2,2051,2050, new BigDecimal("80.00"));
   public static final Transfer TRANSFER3 = new Transfer(3003,2,1,2051,2050, new BigDecimal("600.00"));
   public static final Transfer TRANSFER4 = new Transfer(3004,2,3,2050,2051, new BigDecimal("250.00"));

    private JdbcTransferDao sut;


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);


    }

    @Test
    public void getAllTransfers_Returns_Correctly() {

        List<Transfer> transferList = sut.getAllTransfers();
        Assert.assertEquals(4, transferList.size());
        assertTransfersMatch(TRANSFER1, transferList.get(0));
        assertTransfersMatch(TRANSFER2, transferList.get(1) );
        assertTransfersMatch(TRANSFER3, transferList.get(2) );
        assertTransfersMatch(TRANSFER4, transferList.get(3) );

    }

    @Test
    public void getTransferById_returns_correct_transfer(){
        Transfer transfer = sut.getTransferById(3001);
        assertTransfersMatch(TRANSFER1, transfer);

        transfer = sut.getTransferById(3002);
        assertTransfersMatch(TRANSFER2, transfer);

        transfer = sut.getTransferById(3003);
        assertTransfersMatch(TRANSFER3, transfer);

        transfer = sut.getTransferById(3004);
        assertTransfersMatch(TRANSFER4, transfer);
    }


    private void assertTransfersMatch(Transfer expected, Transfer actual) {
        Assert.assertEquals(expected.getTransferId(), actual.getTransferId());
        Assert.assertEquals(expected.getTransferTypeId(), actual.getTransferTypeId());
        Assert.assertEquals(expected.getTransferStatusId(), actual.getTransferStatusId());
        Assert.assertEquals(expected.getAccountFrom(), actual.getAccountFrom());
        Assert.assertEquals(expected.getAccountTo(), actual.getAccountTo());
        Assert.assertEquals(expected.getAmount(), actual.getAmount());

    }





}
