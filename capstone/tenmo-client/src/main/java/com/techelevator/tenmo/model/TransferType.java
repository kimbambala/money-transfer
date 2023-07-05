package com.techelevator.tenmo.model;

public class TransferType {

    private int transferTypeId;
    private String transferStatusDesc;

    public TransferType(int transferTypeId, String transferStatusDesc) {
        this.transferTypeId = transferTypeId;
        this.transferStatusDesc = transferStatusDesc;
    }

    public TransferType() {

    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }

    @Override
    public String toString() {
        return "TransferType{" +
                "transferTypeId=" + transferTypeId +
                ", transferStatusDesc='" + transferStatusDesc + '\'' +
                '}';
    }
}
