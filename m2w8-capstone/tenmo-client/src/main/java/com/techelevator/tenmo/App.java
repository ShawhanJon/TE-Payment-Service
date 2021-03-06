package com.techelevator.tenmo;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferDTO;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AccountServiceException;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.RestAccountService;
import com.techelevator.tenmo.services.RestTransferService;
import com.techelevator.tenmo.services.TransferServiceException;
import com.techelevator.view.ConsoleService;
import com.techelevator.tenmo.models.Account;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN,
			MENU_OPTION_EXIT };
	
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS,
			MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS,
			MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
	private static final String PENDING_REQUEST_MENU_VIEW_PENDING = "View your pending requests";
	private static final String PENDING_REQUEST_MENU_VIEW_REQUEST_TRANSFERS = "View all of your Request Transfers";
	private static final String PENDING_REQUEST_MENU_RECONCILE_REQUESTS = "Approve or Reject pending Request Transfers";
	private static final String MAIN_MENU = "Go to main menu";
	private static final String[] PENDING_REQUEST_MENU_OPTIONS = {PENDING_REQUEST_MENU_VIEW_PENDING, PENDING_REQUEST_MENU_VIEW_REQUEST_TRANSFERS, PENDING_REQUEST_MENU_RECONCILE_REQUESTS, MAIN_MENU};
			
	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private RestAccountService accountService = new RestAccountService(API_BASE_URL);
	private RestTransferService transferService = new RestTransferService(API_BASE_URL);

	public static void main(String[] args) throws AccountServiceException, TransferServiceException {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public void run() throws AccountServiceException, TransferServiceException {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() throws AccountServiceException, TransferServiceException {
		while (true) {
			String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				pendingMenu();
			} else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void pendingMenu() throws AccountServiceException, TransferServiceException{
		while(true) {
			String choice =(String) console.getChoiceFromOptions(PENDING_REQUEST_MENU_OPTIONS);
			if(PENDING_REQUEST_MENU_VIEW_PENDING.equals(choice)) {
				viewPendingRequests();
			}
			else if(PENDING_REQUEST_MENU_VIEW_REQUEST_TRANSFERS.equals(choice)) {
				viewRequestTransfers();
			}
			else if(PENDING_REQUEST_MENU_RECONCILE_REQUESTS.equals(choice)) {
				reconcilePendingRequests();
			}
			else {
				mainMenu();
			}
		}
	}
	

	private void viewCurrentBalance() throws AccountServiceException {
		System.out.println("Your balance is: $" + accountService.getAccountBalance());

	}

	private void viewTransferHistory() {
		Transfer[] transfers = transferService.getTransfersforUser();
		if (transfers.length == 0) {
			System.out.println("There are no transactions for this user");
		} else
			System.out.println("Your transfer history: \n");
			System.out.println("-------------------------------------------");
			System.out.println("Transfer");
			System.out.println("ID \t From/To \t Amount");
			System.out.println("-------------------------------------------");
			for (Transfer t : transfers) {
				System.out.println(t.viewTransfers(currentUser.getUser()));
			}
			System.out.println();
			int transferId = console.getUserInputInteger("Please enter the transfer ID to view details (0 to cancel)");
			if(transferId != 0)
			{
				System.out.println(transferService.getTransferById(transferId).toString());
			}

	}

	private void viewPendingRequests() throws TransferServiceException {
		Transfer[] pending = transferService.viewPendingTransfers();
		if(pending.length == 0) {
			System.out.println("There are no pending transactions");
		}
		else
			System.out.println("Pending transactions: \n");
			System.out.println("\n ***************************");
			System.out.println("\n  Pending Transfer Details");
			System.out.println("\n ***************************");
			
			for(Transfer p : pending) {
				System.out.println(p.toString());	
			}
	}
	
	private void reconcilePendingRequests() throws TransferServiceException, AccountServiceException {
		viewRequestTransfers();
		
		TransferDTO transferDto = new TransferDTO();
		Transfer pendingTransfer;
		boolean goodInput = false;
		
		while (!goodInput) {
		
			transferDto.setTransferId(console.getUserInputInteger("\nPlease enter the request ID to reconcile"));
			pendingTransfer = transferService.getTransferById(transferDto.getTransferId());
			
			transferDto.setAmount(pendingTransfer.getAmount());
			transferDto.setTransferToId(pendingTransfer.getAccountTo());
			transferDto.setTransferFromId(pendingTransfer.getAccountFrom());
			transferDto.setTransferTypeId(pendingTransfer.getTransferType());
			
			try {
				int selection = console.getUserInputInteger("Please enter '1' to Approve or '2' to Reject request or '0' to return to menu. \n If approved, money will be deducted from your account immediately.");
				
				if(selection == 1) {
					transferDto.setTransferStatusId(2);
					transferService.reconcileTransfer(transferDto);
					goodInput = true;
					System.out.println("Request Closed");
				}
				else if(selection == 2) {
					transferDto.setTransferStatusId(3);
					transferService.reconcileTransfer(transferDto);
					goodInput = true;
					System.out.println("Request Closed");
				}
			}
			catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	private void viewRequestTransfers() throws TransferServiceException, AccountServiceException {
		Transfer[] pending = transferService.viewPendingRequests(currentUser.getUser().getId());
		if(pending.length == 0) {
			System.out.println("There are no pending transfer requests");
			pendingMenu();
		}
		else
			System.out.println("Pending Requests: \n");
			System.out.println("\n ***************************");
			System.out.println("\n  Request Transfer Details");
			System.out.println("\n ***************************");
			
			for(Transfer p : pending) {
				System.out.println(p.toString());	
			}	
	}

	private void sendBucks() throws AccountServiceException {
		TransferDTO transferDto = new TransferDTO();
		boolean goodInput = false;

		transferDto.setTransferTypeId(2);
		transferDto.setTransferStatusId(2);
		
		while (!goodInput) {
		Account[] theAccounts = transferService.viewAvailableAccounts();
		System.out.println("-------------------------------------------");
		System.out.println("User    Username");
		System.out.println("  ID"); 
		System.out.println("-------------------------------------------");
		System.out.println();
		for (Account account : theAccounts) {
			System.out.println("(" + account.getAccountId() + ")      " + account.getUsername());
		}
		System.out.println("----------------------");
		transferDto.setTransferToId(console.getUserInputInteger("\nPlease enter the account ID to transfer to"));
			try {
				transferDto.setAmount(console.getUserInputDouble("Please enter the amount you would like to send"));
				Transfer transfer = transferService.sendTransfer(transferDto);
				goodInput = true;

				System.out.println();
				System.out.println("\n *************************");
				System.out.println("\n Transfer Details");
				System.out.println("\n *************************");
				System.out.println(transfer.toString());
				System.out.println("Approved");
			
			} catch (Exception e) {
				 System.out.println(e.toString());
			}
			
		}

	}

	private void requestBucks() throws TransferServiceException, AccountServiceException {
		TransferDTO transferDto = new TransferDTO();
		boolean goodInput = false;

		transferDto.setTransferTypeId(1);
		transferDto.setTransferStatusId(1);
		
		while (!goodInput) {
		Account[] theAccounts = transferService.viewAvailableAccounts();
		System.out.println("-------------------------------------------");
		System.out.println("             REQUEST TRANSFER  ");
		System.out.println();
		System.out.println("User     Username");
		System.out.println("  ID"); 
		System.out.println("-------------------------------------------");
		System.out.println();
		for (Account account : theAccounts) {
			System.out.println("(" + account.getAccountId() + ")      " + account.getUsername());
		}
		System.out.println("----------------------");
		transferDto.setTransferFromId(console.getUserInputInteger("\nPlease enter the account ID to transfer to"));
			try {
				transferDto.setAmount(console.getUserInputDouble("Please enter the amount you would like to request"));
				goodInput = true;
			} catch (Exception e) {
				e.getMessage();
			}
			Transfer transfer = transferService.requestTransfer(transferDto);
			System.out.println();
			System.out.println("\n *************************");
			System.out.println("\n Transfer Details");
			System.out.println("\n *************************");
			System.out.println(transfer.toString());
			System.out.println("Request has been sent");
			
		}

	}

	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while (!isAuthenticated()) {
			String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
		while (!isRegistered) // will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch (AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: " + e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) // will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				currentUser = authenticationService.login(credentials);
				RestAccountService.AUTH_TOKEN = currentUser.getToken();
				RestTransferService.AUTH_TOKEN = currentUser.getToken();
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: " + e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}