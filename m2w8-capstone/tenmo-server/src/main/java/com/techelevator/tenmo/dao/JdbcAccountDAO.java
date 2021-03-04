package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.tenmo.model.Account;

public class JdbcAccountDAO implements AccountDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

    public JdbcAccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

	public JdbcAccountDAO() {
	}
	
	@Override
	public BigDecimal getBalance(int userId) {
		String sqlString = "SELECT balance "
							+ "FROM accounts "
							+ "WHERE user_id = ?";
		SqlRowSet results = null;
		BigDecimal balance = null;
		
		try {
		results = jdbcTemplate.queryForRowSet(sqlString, userId);
		
		if (results.next()) {
			balance = results.getBigDecimal("balance");
			}
		
		} catch (DataAccessException e) {
			System.out.println("Error accessing data");
			}
		
		return balance;
	}
	
	@Override
	public BigDecimal addToBalance(BigDecimal amountToAdd, int id) {
		Account account = findAccountById(id);
		BigDecimal newBalance = account.getBalance().add(amountToAdd);
		System.out.println(newBalance);
		String sqlString = "UPDATE accounts "
							+ "SET balance = ? "
							+ "WHERE user_id = ?";
		
		try {
			jdbcTemplate.update(sqlString, newBalance, id);
			
		} catch (DataAccessException e) {
			System.out.println("Error accessing data");
			}
		
		return account.getBalance();
	}
	
	@Override
	public BigDecimal subtractFromBalance(BigDecimal amountToSubtract, int id) {
		Account account = findAccountById(id);
		BigDecimal newBalance = account.getBalance().subtract(amountToSubtract);
		System.out.println(newBalance);
		String sqlString = "UPDATE accounts "
							+ "SET balance = ? "
							+ "WHERE user_id = ?";
		
		try {
			jdbcTemplate.update(sqlString, newBalance, id);
			
		} catch (DataAccessException e) {
			System.out.println("Error accessing data");
			}
		
		
		
		return account.getBalance();
	}
	
	@Override
	public Account findUserById(int userId) {
		String sqlString = "SELECT * "
				+ "FROM accounts "
				+ "WHERE user_id = ?";
		Account account = null;

		try {
			SqlRowSet result = jdbcTemplate.queryForRowSet(sqlString, userId);
			account = mapRowToAccount(result);

		} catch (DataAccessException e) {
			System.out.println("Error accessing data");
		}
		
		return account;
	}
	
	@Override
	public Account findAccountById(int id) {
		String sqlString = "SELECT * "
				+ "FROM accounts "
				+ "WHERE user_id = ?";
		Account account = null;
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlString, id);
		
		if (result.next()) {
			account = mapRowToAccount(result);
		}
		
		return account;
	}
	
	private Account mapRowToAccount(SqlRowSet result) {
		Account account = new Account();
		
		account.setBalance(result.getBigDecimal("balance"));
		account.setAccountId(result.getInt("account_id"));
		account.setUserId(result.getInt("user_id"));
		
		return account;
	}


}
