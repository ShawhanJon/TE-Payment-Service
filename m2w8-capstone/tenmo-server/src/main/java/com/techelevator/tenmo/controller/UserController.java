package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@RestController
@PreAuthorize("isAuthenticated()")

//Mapping starts here
@RequestMapping(path = "/users")
public class UserController
{
	@Autowired
	UserDAO userDao;
	@Autowired
	TransferDAO transferDao;
	
//	Mapped as a GET Method using base url of /users
	@GetMapping()
	public List<User> getAll()
	{
		List<User> users = userDao.findAll();
		
		return users;
	}
	
//	Mapped as a GET Method to get balance using /users/balance
	@GetMapping("/balance")
	public BigDecimal getBalance(Principal principal)
	{
		User user = userDao.findByUsername(principal.getName());
		return user.getBalance();
	}
	
//	Mapped as a GET Method to get balance using /users/balance/{id}
	@GetMapping("/balance/{id}")
	public BigDecimal getBalance(@PathVariable int id)
	{
		User user = userDao.findByUserId(id);
		return user.getBalance();
	}
	
//	Mapped as a GET Method to get transfers using /users/transfers/{id}
	@GetMapping("/transfers/{id}")
	public List<Transfer> getTransfersByUser(@PathVariable int id)
	{
		List<Transfer> transfers = userDao.getTransfersByUser(id);
		
		return transfers;
	}
	
}