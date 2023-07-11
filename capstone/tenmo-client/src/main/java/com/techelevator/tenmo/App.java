package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;
import com.techelevator.util.Constants;

import java.math.BigDecimal;
import java.util.Arrays;
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
        } else if(!authenticationService.register(credentials)){
            consoleService.printErrorMessage();
        }else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        System.out.println("Enter your login information");
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser != null) {
            accountService.setAuthToken(currentUser.getToken());
            userService.setAuthToken(currentUser.getToken());
            System.out.println("You are now logged in!");

        }else{
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
        System.out.println("-------------------------------------------");
        System.out.println("Transfers");
        System.out.println("ID          From/To                  Amount");
        System.out.println("-------------------------------------------");
        int userId = currentUser.getUser().getId();

        Transfer[] transfers = transferService.listTransfers(currentUser.getToken());
        for(Transfer transfer : transfers) {
            Account accountOfUser = accountService.getAccountByUserId(userId);

            if (transfer.getAccountFrom() == accountOfUser.getAccountId() || transfer.getAccountTo() == accountOfUser.getAccountId()) {
                System.out.print(transfer.getTransferId() + "         ");
                if (transfer.getAccountFrom() == accountOfUser.getAccountId()) { //where logged in user is the sending account
                    System.out.print("To: ");
                    Account currentReceiver = accountService.getAccountByAccountId(transfer.getAccountTo());
                    int receiverId = currentReceiver.getUserId();
                    System.out.println(userService.getUsernameById(receiverId) + "        $" + transfer.getAmount());
                }else if (transfer.getAccountTo() == accountOfUser.getAccountId()) {
                    System.out.print("From: ");
                    Account currentSender = accountService.getAccountByAccountId(transfer.getAccountFrom()); //where the logged in user is the receiving account
                    int senderId = currentSender.getUserId();
                    System.out.println(userService.getUsernameById(senderId) + "        $" + transfer.getAmount());
                }

                }
            }

        transferDetails();


    }

    private void transferDetails(){
        Transfer[] transfers = transferService.listTransfers(currentUser.getToken());
        int transferId = consoleService.promptForInt("Please enter transfer ID to view details (0 to cancel): ");

        for(Transfer transfer : transfers) {
            int comparedTransferId = transfer.getTransferId();

            if(transferId == comparedTransferId){
                viewTransferDetailsByTransferId(transferId);
                break;
            }

            if (transferId == 0) {
                return;
            }

        }
        Transfer transfer = new Transfer();
        int comparedTransferId = transfer.getTransferId();
        if (transferId != comparedTransferId) {
            System.out.println("Incorrect transfer ID");
        }

    }

    private void viewTransferDetailsByTransferId(int transferId) {

        Transfer transfer = transferService.getTransfer(currentUser.getToken(), transferId);
        System.out.println("-----------------------------------------");
        System.out.println("Transfer Details");
        System.out.println("-----------------------------------------");
        System.out.println("ID: " + transfer.getTransferId());
        Account sendingAccount = accountService.getAccountByAccountId(transfer.getAccountFrom());
        String sendingUserName = userService.getUsernameById(sendingAccount.getUserId());
        System.out.println("From: " + sendingUserName);
        Account receivingAccount = accountService.getAccountByAccountId(transfer.getAccountTo());
        String receivingUserName = userService.getUsernameById(receivingAccount.getUserId());
        System.out.println("To: " + receivingUserName);
        if (transfer.getTransferTypeId() == 2) {
            System.out.println("Type: " + "Send");
        }
        if (transfer.getTransferTypeId() == 1) {
            System.out.println("Type: " + "Request");
        }
        switch (transfer.getTransferStatusId()) {
            case 1:
                System.out.println("Status: " + "Pending");
                break;
            case 2:
                System.out.println("Status: " + "Approved");
                break;
            case 3:
                System.out.println("Status: " + "Rejected");
                break;

        }
        System.out.println("Amount: $" + transfer.getAmount());

    }

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        displayUsers();
        int userId = currentUser.getUser().getId();
        int receiverId = userId;
        while (receiverId == userId) {

            receiverId = consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel): ");
            if (receiverId == userId) {
                System.out.println("You cannot send money to yourself!");
            }
            if (receiverId == 0) {
                return;
            }
        }
        Account sendingAccount = accountService.getAccountByUserId(userId);
        Account receivingAccount = accountService.getAccountByUserId(receiverId);
        BigDecimal currentBalance = sendingAccount.getBalance();
        BigDecimal amountToSend = null;

        while (amountToSend == null || amountToSend.compareTo(BigDecimal.ZERO) < 0) {
            amountToSend = consoleService.promptForBigDecimal("Enter amount: ");
            if (amountToSend.equals(BigDecimal.ZERO)) {
                System.out.println("Cannot be Zero!");
                return;
            }
            if (amountToSend == null || amountToSend.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Invalid amount of money to send!");
            }

        }
        if (currentBalance.compareTo(amountToSend) < 0) {
            System.out.println("Insufficient Funds!");
        } else {
            sendingAccount.setBalance(sendingAccount.getBalance().subtract(amountToSend)); //subtract send amount from sending account
            receivingAccount.setBalance(receivingAccount.getBalance().add(amountToSend));

            //Update the database account balances
            accountService.withdraw(sendingAccount.getAccountId(), amountToSend);
            accountService.deposit(receivingAccount.getAccountId(), amountToSend);


            System.out.println("Sender balance:" + sendingAccount.getBalance());
            System.out.println("Receiver balance:" + receivingAccount.getBalance());
        }
        Transfer transfer = new Transfer(2, 2, 2, sendingAccount.getAccountId(),
                receivingAccount.getAccountId(), amountToSend);
        transferService.createTransfer(currentUser.getToken(), transfer);

    }



	private void requestBucks() {
        displayUsers();
        int userId = currentUser.getUser().getId();
        int senderId = userId;
        while (senderId == userId) {
            senderId = consoleService.promptForInt("Enter ID of user you are requesting from (0 to cancel): ");
            if (senderId == userId) {
                System.out.println("You cannot request money from yourself!");
            }
            if (senderId == 0) {
                return;
            }
        }
        Account sendingAccount = accountService.getAccountByUserId(senderId);
        Account receivingAccount = accountService.getAccountByUserId(userId);
        BigDecimal currentBalance = sendingAccount.getBalance();
        BigDecimal amountToSend = null;

        while (amountToSend == null || amountToSend.compareTo(BigDecimal.ZERO) < 0) {
            amountToSend = consoleService.promptForBigDecimal("Enter amount: ");
            if (amountToSend == null || amountToSend.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Invalid amount of money to request!");
            }

        }

        Transfer transfer = new Transfer(2, 1, 1, sendingAccount.getAccountId(),
                receivingAccount.getAccountId(), amountToSend);
        transferService.createTransfer(currentUser.getToken(), transfer);
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
