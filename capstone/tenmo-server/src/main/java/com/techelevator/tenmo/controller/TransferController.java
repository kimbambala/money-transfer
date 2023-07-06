package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private final TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    @GetMapping
    public List<Transfer> listAllTransfers() {
        return transferDao.getAllTransfers();
    }

    @GetMapping("/{id}")
    public Transfer getTransferById(@PathVariable int id) {
        return transferDao.getTransferById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransfer(@RequestBody Transfer newTransfer) {
        transferDao.save(newTransfer);
    }

    @PutMapping("/{id}")
    public void updateTransfer(@PathVariable int id, @RequestBody Transfer updatedTransfer) {
        transferDao.update(id, updatedTransfer);
    }

    @DeleteMapping("/{id}")
    public void deleteTransfer(@PathVariable int id) {
        transferDao.delete(id);
    }
}
