package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.model.InsufficientFundsException;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;

@RestController
@PreAuthorize("isAuthenticated()")

public class TransfersController {

	private TransfersDAO transferDAO;
	private AccountDAO accountDAO;
	
	public TransfersController(TransfersDAO dao, AccountDAO accountDAO) {
		this.transferDAO = dao;
		this.accountDAO = accountDAO;
	}
	
	@RequestMapping(path = "/transfers", method = RequestMethod.GET)
	public List<Transfer> list(Principal principal, @RequestParam(defaultValue = "-1") int id) {
		if(id == -1) {
			int accountId = (accountDAO.getAccountByUsername(principal.getName())).getAccountId();
			return transferDAO.listAllForUser(accountId);
		}else 
			return transferDAO.getTransferByID(id); 
	}
	
	@RequestMapping(path = "/transfers/pending", method = RequestMethod.GET)
	public List<Transfer> listPending(Principal principal, @RequestParam(defaultValue = "")String transferRequest){
		int accountId = (accountDAO.getAccountByUsername(principal.getName())).getAccountId();
		if(transferRequest.equals("")) {
			return transferDAO.listPendingTransfers(accountId);
		}
		else return transferDAO.listRequests(accountId);
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@RequestMapping(path = "/transfers", method = RequestMethod.PUT)
	public Transfer updateTransfer(@Valid @RequestBody TransferDTO transferDTO, Principal principal) {
		transferDAO.updateRequest(transferDTO);
		if(transferDTO.getTransferStatusId() == 2) {
			Double fromAccountBalance = accountDAO.getAccountById(transferDTO.getTransferFromId()).getBalance();
			Double newFromAccountBalance = fromAccountBalance - transferDTO.getAmount();

			//makes sure the user will not be negative after transaction
			if(newFromAccountBalance >= 0) {
				//writes the transfer to the DB
				int id = transferDAO.newTransfer(transferDTO);
			
				//update balance in fromaccount to subtract amount
				accountDAO.updateBalance(newFromAccountBalance, transferDTO.getTransferFromId());

				//add amount to toaccount
				Double newToAccountBalance = accountDAO.getAccountById(transferDTO.getTransferToId()).getBalance() + transferDTO.getAmount();
				//update balance in toaccount add amount
				accountDAO.updateBalance(newToAccountBalance, transferDTO.getTransferToId());
				
				//returns transfer ID for confirmation
				return transferDAO.getTransferByID(id).get(0);
			}
		}
		return transferDAO.getTransferByID(transferDTO.getTransferId()).get(0);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "/transfers/request", method = RequestMethod.POST)
	public Transfer request(@Valid @RequestBody TransferDTO transferDTO, Principal principal) throws Exception{
		transferDTO.setTransferToId(accountDAO.getAccountByUsername(principal.getName()).getUserId());
		
		return transferDAO.getTransferByID(transferDAO.newTransfer(transferDTO)).get(0);
	
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "/transfers", method = RequestMethod.POST)
	public Transfer transfer(@Valid @RequestBody TransferDTO transferDTO, Principal principal) throws Exception{
	
		transferDTO.setTransferFromId(accountDAO.getAccountByUsername(principal.getName()).getUserId());
		
		Double fromAccountBalance = accountDAO.getAccountById(transferDTO.getTransferFromId()).getBalance();
		Double newFromAccountBalance = fromAccountBalance - transferDTO.getAmount();

		//makes sure the user will not be negative after transaction
		if(newFromAccountBalance >= 0) {
			//writes the transfer to the DB
			int id = transferDAO.newTransfer(transferDTO);
		
			//update balance in fromaccount to subtract amount
			accountDAO.updateBalance(newFromAccountBalance, transferDTO.getTransferFromId());

			//add amount to toaccount
			Double newToAccountBalance = accountDAO.getAccountById(transferDTO.getTransferToId()).getBalance() + transferDTO.getAmount();
			//update balance in toaccount add amount
			accountDAO.updateBalance(newToAccountBalance, transferDTO.getTransferToId());
			
			//returns transfer ID for confirmation
			return transferDAO.getTransferByID(id).get(0);
		}
		else {
			throw new InsufficientFundsException();
		}
	

	}
	
	
	
	
	
	
}
