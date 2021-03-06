package com.techelevator.tenmo.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TransferDTO {
	
	@NotNull
	int transferToId;
	
	String transferToUsername;
	
	@Positive(message = "Amount to send cannot be negative amount")
	Double amount;
	
	@NotNull
	int transferTypeId;
	
	String transferTypeName;
	 
	@NotNull
	int transferStatusId;
	
	String transferStatusName;
	
	int transferFromId;
	
	String transferFromName;
	
	int transferId;
	
	
	public int getTransferId() {
		return transferId;
	}

	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}

	public String getTransferToUsername() {
		return transferToUsername;
	}

	public String getTransferTypeName() {
		return transferTypeName;
	}

	public String getTransferStatusName() {
		return transferStatusName;
	}

	public int getTransferFromId() {
		return transferFromId;
	}

	public String getTransferFromName() {
		return transferFromName;
	}

	public void setTransferToUsername(String transferToUsername) {
		this.transferToUsername = transferToUsername;
	}

	public void setTransferTypeName(String transferTypeName) {
		this.transferTypeName = transferTypeName;
	}

	public void setTransferStatusName(String transferStatusName) {
		this.transferStatusName = transferStatusName;
	}

	public void setTransferFromId(int transferFromId) {
		this.transferFromId = transferFromId;
	}

	public void setTransferFromName(String transferFromName) {
		this.transferFromName = transferFromName;
	}

	public int getTransferToId() {
		return transferToId;
	}
	
	public void setTransferToId(int transferToId) {
		this.transferToId = transferToId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public int getTransferTypeId() {
		return transferTypeId;
	}
	public void setTransferTypeId(int transferTypeId) {
		this.transferTypeId = transferTypeId;
	}

	public int getTransferStatusId() {
		return transferStatusId;
	}

	public void setTransferStatusId(int transferStatusId) {
		this.transferStatusId = transferStatusId;
	}


}
