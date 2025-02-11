package org.example;

import org.example.dao.UserDAO;
import org.example.dao.WalletDAO;
import org.example.dao.impl.UserDAOImpl;
import org.example.dao.impl.WalletDAOImpl;
import org.example.model.User;
import org.example.model.Wallet;
import org.example.service.TransactionManagementService;
import org.example.service.UserRegistrationService;
import org.example.service.impl.TransactionManagementServiceImpl;
import org.example.service.impl.UserRegistrationServiceImpl;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {

        Logger logger = Logger.getLogger(Main.class.getName());

        UserDAO userDAO = new UserDAOImpl();
        WalletDAO walletDAO = new WalletDAOImpl();

        UserRegistrationService userRegistrationService = new UserRegistrationServiceImpl(userDAO, walletDAO);
        TransactionManagementService transactionManagementService = new TransactionManagementServiceImpl(walletDAO);
        //Driver class for selecting next input

        Scanner scanner = new Scanner(System.in);

        while(true) {
            logger.info("Select an option:");
            logger.info("1. Register a new user");
            logger.info("2. Check wallet balance");
            logger.info("3. Add wallet balance");
            logger.info("4. Make a transaction");
            logger.info("5. Fetch all transaction");
            logger.info("10. Exit");

            int input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {

                case 1: {
                    logger.info("Enter the user's name:");
                    String userName = scanner.nextLine();
                    User user = new User(userName);
                    userRegistrationService.registerUser(user);
                    break;
                }

                case 2: {
                    logger.info("Enter the userID :");
                    int userId = scanner.nextInt();
                    Wallet wallet = walletDAO.getWalletByUserId(userId);
                    logger.info("Balance for user is : " +  wallet.getBalanceAmount());
                    break;
                }
                case 3: {
                    logger.info("Enter the userID :");
                    int userId = scanner.nextInt();
                    Wallet wallet = walletDAO.getWalletByUserId(userId);
                    if(Objects.isNull(wallet))
                        break;
                    logger.info("Enter the Amount :");
                    double amount = scanner.nextDouble();
                    wallet.addBalanceAmount(amount);
                    logger.info("Balance Added");
                    break;
                }
                case 4: {
                    logger.info("Enter the sender userID :");
                    int senderUserId = scanner.nextInt();
                    logger.info("Enter the receiver userID :");
                    int receiverUserId = scanner.nextInt();
                    logger.info("Enter the Amount :");
                    double amount = scanner.nextDouble();

                    transactionManagementService.processTransaction(senderUserId, receiverUserId, amount);

                    break;
                }
                case 5: {
                    logger.info("Enter the userID :");
                    int userId = scanner.nextInt();
                    logger.info("Enter the Sort Basis, 1 for Amount, 2 for Date:");
                    int sortingNum = scanner.nextInt();

                    transactionManagementService.getTransactions(userId, sortingNum);

                    break;
                }
                case 10: {
                    logger.info("Exiting");
                    scanner.close();
                    return;
                }

                default:
                    logger.warning("Invalid input, please try again.");
            }


        }
    }


}