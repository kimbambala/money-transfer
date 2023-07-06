package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;
import com.techelevator.util.Constants;

import java.math.BigDecimal;
import java.util.List;


public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";


    private final ConsoleService consoleService = new ConsoleService();
    private final AccountService accountService = new AccountService();
    private final TransferService transferService = new TransferService(API_BASE_URL);
    private final UserService userService = new UserService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        accountService.setAuthToken(currentUser.getToken());
        userService.setAuthToken(currentUser.getToken());
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {

        int userId = currentUser.getUser().getId();
        Account currentAccount = accountService.getAccountByUserId(userId);
        BigDecimal balance = currentAccount.getBalance();

        System.out.println("Your current account balance is $" + balance);


	}

    private BigDecimal getBalance(int userId){
        Account currentAccount = accountService.getAccountByUserId(userId);
        BigDecimal balance = currentAccount.getBalance();
        return balance;
    }

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
		
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        displayUsers();
        int userId = currentUser.getUser().getId();
        int receiverId = consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel): ");
        BigDecimal amountToSend = consoleService.promptForBigDecimal("Enter amount: ");
        Account sendingAccount = accountService.getAccountByUserId(userId);
        Account receivingAccount = accountService.getAccountByUserId(receiverId);
        sendingAccount.setBalance(sendingAccount.getBalance().subtract(amountToSend)); //subtract send amount from sending account
        receivingAccount.setBalance(receivingAccount.getBalance().add(amountToSend));
        System.out.println("Sender balance:" + sendingAccount.getBalance());
        System.out.println("Receiver balance:" + receivingAccount.getBalance());

    }



	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

    private void displayUsers() {
        System.out.println("-------------------------------------------");
        System.out.println("Users");
        System.out.println("ID          Name");
        System.out.println("-------------------------------------------");

        // Call to UserService to get all users
        List<User> users = userService.getUsers();

        // Print the users
        for (User user : users) {
            System.out.println(user.getId() + "         " + user.getUsername());
        }
        System.out.println("---------");
    }

}
