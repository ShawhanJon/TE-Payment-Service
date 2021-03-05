package com.techelevator.tenmo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;


@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

	@Autowired
	private TransferDAO transferDAO;
	
	

	@RequestMapping(path = "account/transfers/{id}", method = RequestMethod.GET) 
	public List<Transfer> getAllTransfers(@PathVariable int id) {
		List<Transfer> output = transferDAO.getAllTransfers(id);
		return output;
	}
	
	@RequestMapping(path = "transfers/{id}", method = RequestMethod.GET) 
	public Transfer getSelectedTransfer(@PathVariable int id) {
		Transfer transfer = transferDAO.getTransferById(id);
		return transfer;
	}
	
	@RequestMapping(path = "transfer", method = RequestMethod.POST) 
	public String sendTransfer(@RequestBody Transfer transfer) {
		String results = transferDAO.sendTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
		return results;
	}
	
	@RequestMapping(path = "request", method = RequestMethod.POST) 
	public String requestTransfer(@RequestBody Transfer transfer) {
		String results = transferDAO.requestTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
		return results;
	}
	
	@RequestMapping(path = "request/{id}", method = RequestMethod.GET) 
	public List<Transfer> getAllRequests(@PathVariable int id) {
		List<Transfer> output = transferDAO.getPendingRequests(id);
		return output;
	}
	
	@RequestMapping(path = "transfer/status/{statusId}", method = RequestMethod.PUT) 
	public String updateRequest(@RequestBody Transfer transfer, @PathVariable int statusId) {
		String output = transferDAO.updateRequest(transfer, statusId);
		return output;
	}
}
