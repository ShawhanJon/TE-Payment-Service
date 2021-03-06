package com.techelevator.tenmo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.view.ConsoleService;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				printBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private Account viewCurrentBalance() {
		Account account = new Account();
		try {
			account = restTemplate.exchange(API_BASE_URL + "accounts/" + currentUser.getUser().getId() , HttpMethod.GET, makeEntity(), Account.class).getBody();
		}
		catch (RestClientResponseException e){
			System.out.println(e.getRawStatusCode() + ": " + e.getResponseBodyAsString());
		}

		return account;
	}
	public HttpEntity makeEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(currentUser.getToken());
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
		
	}
	public HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(currentUser.getToken());
		HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
		return entity;
	}
	
	public void printBalance() {
		Account account = new Account();
		try {
			account = restTemplate.exchange(API_BASE_URL + "accounts/" + currentUser.getUser().getId() , HttpMethod.GET, makeEntity(), Account.class).getBody();
		}
		catch (RestClientResponseException e){
			System.out.println(e.getRawStatusCode() + ": " + e.getResponseBodyAsString());
		}
		System.out.println("Your account balance is: $" + account.getBalance());
	}
	
	public void printUpdatedBalance() {
		Account account = new Account();
		try {
			account = restTemplate.exchange(API_BASE_URL + "accounts/" + currentUser.getUser().getId() , HttpMethod.GET, makeEntity(), Account.class).getBody();
		}
		catch (RestClientResponseException e){
			System.out.println(e.getRawStatusCode() + ": " + e.getResponseBodyAsString());
		}
		System.out.println("Your account balance is now: $" + account.getBalance());
	}
	
	private User[] getAllUsers() {
		
		User[] users = null;
		try {
			users = restTemplate.exchange(API_BASE_URL + "users", HttpMethod.GET, makeEntity(), User[].class).getBody();
		}
		catch (RestClientResponseException e) {
			System.out.println(e.getRawStatusCode() + e.getMessage());
		}
		
		for (User s : users) {
			console.printUsers(s);
		}
		return users;
	}

	private Transfer[] viewTransferHistory() {
		Transfer[] transfers = null;
		
		try {
			transfers = restTemplate.exchange(API_BASE_URL + "users/" + currentUser.getUser().getId() + "/transfers/history", HttpMethod.GET, makeEntity(), Transfer[].class).getBody();
		}
		catch (RestClientResponseException e) {
			System.out.println(e.getRawStatusCode() + e.getMessage());
		}
		for(Transfer t : transfers) {
			console.printTransfers(t);
		}
		long selection = console.getUserInputInteger("Enter ID to view details (0 to cancel)");
		if (selection != 0) {
			console.printTransferDetails(transfers, selection);
		}
			
			
			
			
		return transfers;
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	public Transfer sendBucks() {
		getAllUsers();
		System.out.print("\n");
		long id = console.getUserInputInteger("Enter ID of user you are sending to (0 to cancel)");
		String amountString = console.getUserInput("Enter Amount");
		System.out.print("\n");
		double amount = Double.parseDouble(amountString);
		if(id == currentUser.getUser().getId()) {
			System.out.println("You can send TE Bucks to yourself!");
			return null;
		}
		if(amount > viewCurrentBalance().getBalance()) {
			System.out.println("You dont have the TE Bucks required!");
			return null;
		}
		else {
		Transfer transfer = new Transfer();
		transfer.setAmount(amount);
		transfer.setSenderID(currentUser.getUser().getId());
		transfer.setRecipientID(id);
			
		try {
			transfer = restTemplate.postForObject(API_BASE_URL + "users/" + currentUser.getUser().getId() + "/transfers/" + id, makeTransferEntity(transfer), Transfer.class);
		} catch (RestClientResponseException ex) {
			System.out.println(ex.getRawStatusCode() + ": " + ex.getResponseBodyAsString());
		}
		System.out.println("----------------------------------");
		System.out.print("\n");
		System.out.println("TransferID: " + transfer.getTransferID());
		System.out.println("Sender ID: " + transfer.getRecipientID());	
		System.out.println("Recipient ID: " + transfer.getSenderID());
		printUpdatedBalance();
		System.out.print("\n");
		System.out.println("----------------------------------");
		
		return transfer;
		}
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}
	
	private void exitProgram() {
		System.out.println("Goodbye!");
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
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
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
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
