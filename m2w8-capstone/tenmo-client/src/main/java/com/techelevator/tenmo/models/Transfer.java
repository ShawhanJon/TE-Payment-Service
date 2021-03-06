package com.techelevator.tenmo.models;

public class Transfer {

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
	
	public String viewTransfers(User user) {
		if(fromUsername.equals(user.getUsername())) {
			return transferId + "\t TO:   " + toUsername + "\t $" + amount;
		}
		else return transferId + "\t FROM: " + fromUsername+ "\t $" + amount;
	}
				
	@Override	
	public String toString() {
		return	"\n ***************************" +
				"\n  Request Transfer Details" +
				"\n ***************************" +
				"\n ID: " + transferId +
				"\n Type: " + transferType + " " + typeName +
				"\n Status: " + statusId + " " + statusName +
				"\n From: " + accountFrom + " " + fromUsername + 
				"\n To: " + accountTo + " " + toUsername + 
				"\n Amount: "+ amount;
	}

	public Transfer(int transferId, int transferType, String typeName, int statusId, String statusName, int accountFrom,
		String fromUsername, int accountTo, String toUsername, double amount) {
		this.transferId = transferId;
		this.transferType = transferType;
		this.typeName = typeName;
		this.statusId = statusId;
		this.statusName = statusName;
		this.accountFrom = accountFrom;
		this.fromUsername = fromUsername;
		this.accountTo = accountTo;
		this.toUsername = toUsername;
		this.amount = amount;
	}

	public String getToUser() {
		return toUsername;
	}

	public void setToUser(String userName) {
		this.toUsername = userName;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}

	public String getToUsername() {
		return toUsername;
	}

	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}

}