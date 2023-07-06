package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    public Account getAccountByAccountId(int accountId){
        Account account = new Account();
        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(API_BASE_URL + "account/" + accountId, HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return account;
    }

    public BigDecimal getBalance(int accountId) {
        Account account = getAccountByAccountId(accountId);
        return account.getBalance();

    }

    public Account getAccountByUserId(int userId){
        Account account = new Account();
        try {
            ResponseEntity<Account> response = restTemplate.exchange(API_BASE_URL + "account/" + "user/" + userId, HttpMethod.GET, makeAuthEntity(), Account.class);
            
            account = response.getBody();
        }catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return account;
    }

    public void withdraw(int accountId, BigDecimal amount) {
        restTemplate.put(API_BASE_URL + "accounts/" + accountId + "withdraw/" + amount, null);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Account> makeAccountEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(account, headers);
    }
}
