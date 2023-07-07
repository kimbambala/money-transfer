package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    List<Transfer> getAllTransfers();

    Transfer getTransferById(int id);

    void save(Transfer newTransfer);

    void update(int id, Transfer transfer);

    void delete(int id);
}
