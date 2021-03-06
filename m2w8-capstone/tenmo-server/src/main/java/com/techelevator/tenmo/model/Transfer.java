package com.techelevator.tenmo.model;

import java.security.Principal;

import com.techelevator.tenmo.dao.AccountDAO;

public class Transfer {
	private AccountDAO adao;
	private int transferId;
	private int transferType;
	private String typeName;
	private int statusId;
	private String statusName;
	private int accountFrom;
	private String fromUsername;
	private int accountTo;
	private String toUsername;
	private double amount;
	
	
	public Transfer() {	
	}
	
	public Transfer(int transferType, String typeName, int statusId, String statusName, int accountFrom, String fromUsername, int accountTo, String toUsername, double amount) {
	    this.statusId = statusId;
	    this.statusName = statusName;
	    this.transferType = transferType;
	    this.typeName = typeName;
	    this.accountFrom = accountFrom;
	    this.fromUsername = fromUsername;
	    this.accountTo = accountTo;
	    this.toUsername = toUsername;
	    this.amount = amount;
	} 

	public String getTypeName() {
		return typeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public String getToUsername() {
		return toUsername;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}

	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}

	public int getTransferId() {
		return transferId;
	}

	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}

	public int getTransferType() {
		return transferType;
	}

	public void setTransferType(int transferType) {
		this.transferType = transferType;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public int getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(int accountFrom) {
		this.accountFrom = accountFrom;
	}

	public int getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(int accountTo) {
		this.accountTo = accountTo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}