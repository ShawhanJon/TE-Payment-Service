package com.techelevator.tenmo.models;

public class Account {
	private int accountId;
	private int userId;
	private Double balance;
	private String username;
	
	public Account() {
		
	}
	public Account(int accountId, int userId, Double balance, String username) {
		this.accountId = accountId;
		this.userId = userId;
		this.balance = balance;
		this.username = username;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "Account |AccountID= " + accountId + ", userID= " + userId + ", username= " + username + ", balance= " + balance + "|";
	}
}
