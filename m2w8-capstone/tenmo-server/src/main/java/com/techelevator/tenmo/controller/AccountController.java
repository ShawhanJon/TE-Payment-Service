package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.jdbc.JDBCAccountDAO;
import com.techelevator.tenmo.model.Account;

@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {

	private AccountDAO accountDAO;
	 
	public AccountController(AccountDAO dao) {
		this.accountDAO = dao;
	}
	
	@RequestMapping(path = "/accounts", method = RequestMethod.GET)
	public List<Account> list(Principal principal){
		return accountDAO.list(accountDAO.getAccountByUsername(principal.getName()).getAccountId());
	}
	
	@RequestMapping(path = "/accounts/{id}", method = RequestMethod.GET)
	public Account get(@PathVariable int id) {
		return accountDAO.getAccountById(id);
	}
	
	@RequestMapping(path = "/accounts/{id}/balance", method = RequestMethod.GET)
	public Double getBalance(@PathVariable int id) {
		return accountDAO.getAccountById(id).getBalance();
	}
	
	@RequestMapping(path = "/accounts/balance", method = RequestMethod.GET)
	public Double getBalance(Principal principal) {
		return (accountDAO.getAccountByUsername(principal.getName())).getBalance();
	}
	
	
}
