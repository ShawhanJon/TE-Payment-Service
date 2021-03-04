package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TenmoDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.User;

@RestController
@PreAuthorize("isAuthenticated()")
public class TenmoController {
	
	private TenmoDAO tenmoDao;
	public TenmoController(UserDAO userDao) {
		this.tenmoDao = tenmoDao;
	}
	

	
	@PreAuthorize("permitAll")
	@RequestMapping(path = "/user", method = RequestMethod.GET) //method #1
	public void viewCurrentBalance() {
	}
	
	

	
	
}
