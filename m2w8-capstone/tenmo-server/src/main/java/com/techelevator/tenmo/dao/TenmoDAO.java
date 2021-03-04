package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.User;



public interface TenmoDAO {
	
	public void viewCurrentBalance();

	public void viewTransferHistory();
	
	public void viewPendingRequests();

	public double sendBucks();
	
	public void requestBucks();

	
	
}
