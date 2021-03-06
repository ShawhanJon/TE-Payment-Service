package com.techelevator.tenmo.dao.jdbc;
 
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;

@Component
public class JDBCTransfersDAO implements TransfersDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCTransfersDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Transfer> listAllForUser(int accountId) {
		List<Transfer> transfers = new ArrayList<>();
		String sql = "SELECT t.transfer_id, t.transfer_type_id, tt.transfer_type_desc, t.transfer_status_id, ts.transfer_status_desc, t.account_from, u1.username AS from_user, t.account_to, u2.username AS to_user, t.amount " 
				   + "FROM transfers t "
				   + "JOIN transfer_types tt USING(transfer_type_id) "
				   + "JOIN transfer_statuses ts USING(transfer_status_id) "  
				   + "JOIN accounts a1 ON t.account_from = a1.account_id " 
				   + "JOIN accounts a2 ON t.account_to = a2.account_id " 
				   + "JOIN users u1 ON a1.account_id = u1.user_id "  
				   + "JOIN users u2 ON a2.account_id = u2.user_id " 
				   + "WHERE t.account_from = ? OR t.account_to = ?"
				   + "ORDER BY transfer_id";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
		while(results.next()) {
			Transfer transferResult = mapRowToTransfer(results);
			transfers.add(transferResult);
		}
		return transfers;
	}
	
	@Override
	public List<Transfer> listRequests(int accountId) {
		List<Transfer> transfers = new ArrayList<>();
		String sql = "SELECT t.transfer_id, t.transfer_type_id, tt.transfer_type_desc, t.transfer_status_id, ts.transfer_status_desc, t.account_from, u1.username AS from_user, t.account_to, u2.username AS to_user, t.amount " 
				   + "FROM transfers t "
				   + "JOIN transfer_types tt USING(transfer_type_id) "
				   + "JOIN transfer_statuses ts USING(transfer_status_id) "  
				   + "JOIN accounts a1 ON t.account_from = a1.account_id " 
				   + "JOIN accounts a2 ON t.account_to = a2.account_id " 
				   + "JOIN users u1 ON a1.account_id = u1.user_id "  
				   + "JOIN users u2 ON a2.account_id = u2.user_id " 
				   + "WHERE t.account_from = ? AND t.transfer_status_id = 1 AND t.transfer_type_id = 1 "
				   + "ORDER BY transfer_id";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
		while(results.next()) {
			Transfer transferResult = mapRowToTransfer(results);
			transfers.add(transferResult);
		}
		return transfers;
	}

	@Override
	public int newTransfer(TransferDTO transferDTO) {
		String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)"
				   + "VALUES	  			(?, 			   ?, 				   ?, 			 ?, 		 ?)"
				   + "RETURNING transfer_id";
	
		 Long transferIdLong = jdbcTemplate.queryForObject(sql, Long.class, transferDTO.getTransferTypeId(), transferDTO.getTransferStatusId(), transferDTO.getTransferFromId(), transferDTO.getTransferToId(), transferDTO.getAmount());
		 int transferId = transferIdLong.intValue();
		 return transferId;
	}
	@Override
	public List<Transfer> listPendingTransfers(int accountId){
		List<Transfer> pending = new ArrayList<>();
		String sql = "SELECT t.transfer_id, t.transfer_type_id, tt.transfer_type_desc, t.transfer_status_id, ts.transfer_status_desc, t.account_from, u1.username AS from_user, t.account_to, u2.username AS to_user, t.amount " 
				   + "FROM transfers t "
				   + "JOIN transfer_types tt USING(transfer_type_id) "
				   + "JOIN transfer_statuses ts USING(transfer_status_id) "  
				   + "JOIN accounts a1 ON t.account_from = a1.account_id " 
				   + "JOIN accounts a2 ON t.account_to = a2.account_id " 
				   + "JOIN users u1 ON a1.account_id = u1.user_id "  
				   + "JOIN users u2 ON a2.account_id = u2.user_id " 
				   + "WHERE (account_from = ? OR account_to = ?) AND transfer_status_id = 1"
				   + "ORDER BY t.transfer_id";
			
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
		while(results.next()) {
			Transfer transferResult = mapRowToTransfer(results);
			pending.add(transferResult);
		} 
		return pending;
	}  
	
	@Override
	public List<Transfer> getTransferByID(int transferId) {
		List<Transfer> transfer = new ArrayList<>();
		String sql = "SELECT t.transfer_id, t.transfer_type_id, tt.transfer_type_desc, t.transfer_status_id, ts.transfer_status_desc, t.account_from, u1.username AS from_user, t.account_to, u2.username AS to_user, t.amount\n" + 
				" " 
				   + "FROM transfers t "
				   + "JOIN transfer_types tt USING(transfer_type_id) "
				   + "JOIN transfer_statuses ts USING(transfer_status_id) "  
				   + "JOIN accounts a1 ON t.account_from = a1.account_id " 
				   + "JOIN accounts a2 ON t.account_to = a2.account_id " 
				   + "JOIN users u1 ON a1.account_id = u1.user_id "  
				   + "JOIN users u2 ON a2.account_id = u2.user_id " 
				   + "WHERE transfer_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
		
		while(results.next()) {
			Transfer transferResult = mapRowToTransfer(results);
			transfer.add(transferResult);
		}
		return transfer;
		
	}
	
	@Override
	public void updateRequest(TransferDTO transfer) {
		String sql = "UPDATE transfers SET transfer_status_id = ? WHERE transfer_id = ? ";
		jdbcTemplate.update(sql, transfer.getTransferStatusId(), transfer.getTransferId());
	}
	
	private Transfer mapRowToTransfer(SqlRowSet results) {
		Transfer transfer = new Transfer();
		
		transfer.setTransferId(results.getInt("transfer_id"));
		transfer.setTransferType(results.getInt("transfer_type_id"));
		transfer.setTypeName(results.getString("transfer_type_desc"));
		transfer.setStatusId(results.getInt("transfer_status_id"));
		transfer.setStatusName(results.getString("transfer_status_desc"));
		transfer.setAccountFrom(results.getInt("account_from"));
		transfer.setFromUsername(results.getString("from_user"));
		transfer.setAccountTo(results.getInt("account_to"));
		transfer.setToUsername(results.getString("to_user"));
		transfer.setAmount(results.getDouble("amount"));
		
		return transfer;
	}

	
}