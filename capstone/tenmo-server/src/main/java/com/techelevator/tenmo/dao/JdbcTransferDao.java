package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<Transfer> getAllTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }

        return transfers;
    }

    @Override
    public Transfer getTransferById(int id) {
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

        if (results.next()) {
            return mapRowToTransfer(results);
        }

        throw new RuntimeException("Transfer id " + id + " was not found.");
    }

    @Override
    public void save(Transfer newTransfer) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, newTransfer.getTransferTypeId(), newTransfer.getTransferStatusId(),
                newTransfer.getAccountFrom(), newTransfer.getAccountTo(), newTransfer.getAmount());
    }

    @Override
    public void update(int id, Transfer transfer) {
        String sql = "UPDATE transfer SET transfer_type_id = ?, transfer_status_id = ?, account_from = ?, " +
                "account_to = ?, amount = ? WHERE transfer_id = ?";
        jdbcTemplate.update(sql, transfer.getTransferTypeId(), transfer.getTransferStatusId(),
                transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount(), id);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM transfer WHERE transfer_id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();

        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));

        return transfer;
    }
}
