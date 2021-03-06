package com.techelevator;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

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
		
		String sql = "SELECT COUNT(*) AS numberOfRows FROM accounts";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
		rows.next();
		int originalRowCount = rows.getInt("numberOfRows");

		
	}
	
	@Test
	public void testSomethingElse() {
		
	}
	

	
	
}

