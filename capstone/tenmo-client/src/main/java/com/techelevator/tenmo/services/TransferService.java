package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private final String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferService(String url) {
        BASE_URL = url;
    }

    public Transfer[] listTransfers(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Transfer[]> response = restTemplate.exchange(
                BASE_URL + "transfers",
                HttpMethod.GET,
                entity,
                Transfer[].class);

        return response.getBody();
    }

    public Transfer getTransfer(String token, int transferId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Transfer> response = restTemplate.exchange(
                BASE_URL + "transfers/" + transferId,
                HttpMethod.GET,
                entity,
                Transfer.class);

        return response.getBody();
    }

    public Transfer createTransfer(String token, Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);

        ResponseEntity<Transfer> response = restTemplate.exchange(
                BASE_URL + "transfers",
                HttpMethod.POST,
                entity,
                Transfer.class);

        return response.getBody();
    }
}
