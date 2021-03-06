package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

public interface AccountDAO {
	
	public Account getAccount(long id);
	public Transfer makeTransfer(Transfer transfer);
	List<Transfer> getTransferHistory(long id);
	public void addToBalance(Transfer transfer);
	public void subtractFromBalance(Transfer transfer);

}
