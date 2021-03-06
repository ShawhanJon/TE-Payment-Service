package com.techelevator;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.tenmo.dao.jdbc.JDBCUserDAO;


public class UserDAOTest extends DAOIntegrationTest {
    private static DataSource dataSource;

    	private JDBCUserDAO dao;	

     
     @BeforeEach
	public void setup() {
		String sqlInsertUser = "INSERT INTO account (userId) VALUES (?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertUser);
		//dao = new JdbcUserDAO(dataSource);
	}
	
     
	@Test
	public void returns_balance_by_user_id() {

	}
	
	
	
	@Test
	public void testSomethingElse() {
		
	}
     
     
     
     
}
