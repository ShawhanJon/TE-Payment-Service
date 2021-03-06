package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.User;
import com.techelevator.view.ConsoleService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import com.techelevator.tenmo.models.Account;

public class RestAccountService {
	public static String AUTH_TOKEN = "";
	private final String BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();
	private Account account = new Account();

	public RestAccountService(String url) {
		this.BASE_URL = url;
	}

	public Double getAccountBalance(int accountId) throws AccountServiceException {
		Double balance = null;
		try {
			balance = restTemplate.exchange(BASE_URL + "accounts/" + account.getAccountId() + "/balance",
					HttpMethod.GET, makeAuthEntity(), Double.class).getBody();
		} catch (RestClientResponseException ex) {
			throw new AccountServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		} catch (ResourceAccessException ex) {
			throw new AccountServiceException(ex.getMessage());
		}
		return balance;
	}

	public Double getAccountBalance() throws AccountServiceException {
		Double balance = null;
		try {
			balance = restTemplate.exchange(BASE_URL + "accounts/balance", HttpMethod.GET, makeAuthEntity(), Double.class).getBody();
		} catch (RestClientResponseException ex) {
			throw new AccountServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		} catch (ResourceAccessException ex) {
			throw new AccountServiceException(ex.getMessage());
		}
		return balance;
	}

	public User[] listAllUsers() throws AccountServiceException {
		User[] users = null;
		try {
			users = restTemplate.exchange(BASE_URL + "users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
		} catch (RestClientResponseException ex) {
			throw new AccountServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		} catch (ResourceAccessException ex) {
			throw new AccountServiceException(ex.getMessage());
		}
		return users;
	}

	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}

}