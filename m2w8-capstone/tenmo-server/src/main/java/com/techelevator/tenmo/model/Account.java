package com.techelevator.tenmo.model;

public class Account {
	private int accountId;
	private int userId;
	private Double balance;
	private String username;
	
	public Account() {
		
	}
	public Account(int userId, Double balance, String username, int accountId) {
		this.username = username;
		this.userId = userId;
		this.balance = balance;
		this.accountId = accountId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
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
	@Override
	public String toString() {
		return "Account | accountId= " + accountId + ", userId=" + userId + ", balance=" + balance + "|";
	}
}
