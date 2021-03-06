package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@RestController
public class Controller {

	private AccountDAO accountDAO;
	private UserDAO userDAO;

	public Controller(AccountDAO accountDAO, UserDAO userDAO) {
		this.accountDAO = accountDAO;
		this.userDAO = userDAO;
	}

	@RequestMapping(path = "accounts/{id}", method = RequestMethod.GET)
	public Account getAccount(@PathVariable long id) {
		return accountDAO.getAccount(id);
	}

	@RequestMapping(path = "users", method = RequestMethod.GET)
	public List<User> getListOfUsers() {
		return userDAO.findAll();
	}

	@RequestMapping(path = "users/{id}/transfers/{id}", method = RequestMethod.POST)
	public Transfer makeTransfer(@PathVariable("id") Long senderID, Long recipientID, @RequestBody Transfer transfer) {
		
		accountDAO.addToBalance(transfer);
		accountDAO.subtractFromBalance(transfer);
		
		
		return accountDAO.makeTransfer(transfer);
	}
	
	@RequestMapping(path = "users/{id}/transfers/history", method = RequestMethod.GET)
	public List<Transfer> getTransferHistory(@PathVariable Long id) {
		return accountDAO.getTransferHistory(id);
	}

}