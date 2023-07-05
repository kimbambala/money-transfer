package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private final String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferService(String url) {
        BASE_URL = url;
    }

    public Transfer[] listTransfers(String token) {
        return restTemplate.getForObject(BASE_URL + "transfers", Transfer[].class);
    }

    public Transfer getTransfer(String token, int transferId) {
        return restTemplate.getForObject(BASE_URL + "transfers/" + transferId, Transfer.class);
    }

    public Transfer createTransfer(String token, Transfer transfer) {
        return restTemplate.postForObject(BASE_URL + "transfers", transfer, Transfer.class);
    }


}