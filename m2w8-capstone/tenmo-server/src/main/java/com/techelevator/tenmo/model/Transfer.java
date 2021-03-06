package com.techelevator.tenmo.model;

public class Transfer {
	
	private long transferID;
	private String senderUsername;
	private long senderID;
	private String recipientUsername;
	private long recipientID;
	private double amount;
	private boolean isApproved;
	private String type;
	
	
	
	
	public long getTransferID() {
		return transferID;
	}
	public void setTransferID(long transferID) {
		this.transferID = transferID;
	}
	public long getSenderID() {
		return senderID;
	}
	public void setSenderID(long senderID) {
		this.senderID = senderID;
	}
	public long getRecipientID() {
		return recipientID;
	}
	public void setRecipientID(long recipientID) {
		this.recipientID = recipientID;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSenderUsername() {
		return senderUsername;
	}
	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}
	public String getRecipientUsername() {
		return recipientUsername;
	}
	public void setRecipientUsername(String recipientUsername) {
		this.recipientUsername = recipientUsername;
	}
	
	
	
	
	
	

}
