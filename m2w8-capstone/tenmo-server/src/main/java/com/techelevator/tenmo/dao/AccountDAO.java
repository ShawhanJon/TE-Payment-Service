package com.techelevator.tenmo.dao;

import java.util.List;
import com.techelevator.tenmo.model.Account;

public interface AccountDAO {
	
	List<Account> list(int id);
				
	Double updateBalance(Double newBalance, int id);

	Account getAccountById(int id);
	
	Account getAccountByUsername(String username);
	
	public void save(Account newAccount);
	
}
