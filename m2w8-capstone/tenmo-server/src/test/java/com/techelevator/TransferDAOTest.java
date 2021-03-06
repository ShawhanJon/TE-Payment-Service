package com.techelevator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;

public class TransferDAOTest extends DAOIntegrationTest{
	

	private TransferDAO dao;
	private UserDAO userdao;
	private JdbcTemplate jdbcTemplate;
	
	
	@BeforeAll
	public void setup() {
		
			//dao = new AccountDAO(getDataSource());
			jdbcTemplate = new JdbcTemplate(getDataSource());
		}
	
	@Test
	public void test_get_balance() {
		
		
		
		
	}
}

