package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;

public interface TransfersDAO {
	
	List<Transfer> listAllForUser(int accountId);
	
	List<Transfer> listPendingTransfers(int accountId);
		
	List<Transfer> getTransferByID(int transferId);

	List<Transfer> listRequests(int accountId);

	void updateRequest(TransferDTO transfer);

	int newTransfer(TransferDTO transferDTO);

}
