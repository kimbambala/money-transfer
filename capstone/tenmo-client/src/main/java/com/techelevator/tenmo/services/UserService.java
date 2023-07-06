package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class UserService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public List<User> getUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity<User[]> responseEntity = restTemplate.exchange(API_BASE_URL + "users", HttpMethod.GET, entity, User[].class);

        return Arrays.asList(responseEntity.getBody());
    }

    public String getUsernameById(int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity<User> responseEntity = restTemplate.exchange(API_BASE_URL + "users/" + id, HttpMethod.GET, entity, User.class);

        return responseEntity.getBody().getUsername();
    }

}

