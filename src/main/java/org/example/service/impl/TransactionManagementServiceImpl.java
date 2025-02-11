package org.example.service.impl;

import org.example.dao.WalletDAO;
import org.example.model.Transaction;
import org.example.model.Wallet;
import org.example.service.TransactionManagementService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class TransactionManagementServiceImpl implements TransactionManagementService {



    private static final Logger logger = Logger.getLogger(TransactionManagementServiceImpl.class.getName());
    private WalletDAO walletDAO;


    public TransactionManagementServiceImpl(WalletDAO walletDAO) {
        this.walletDAO = walletDAO;
    }

    public boolean applyTransaction(int senderUserId, int receiverUserId, double amount) {

        try {

            Wallet senderWallet = walletDAO.getWalletByUserId(senderUserId);
            Wallet receiverWallet = walletDAO.getWalletByUserId(receiverUserId);
            if(Objects.isNull(senderWallet) || Objects.isNull(receiverWallet)) {
                logger.warning("Invalid user IDs");
                return false;
            }
            if (!validateTransaction(senderWallet, amount)) {
                return false;
            }

            senderWallet.deductBalanceAmount(amount);
            receiverWallet.addBalanceAmount(amount);
            Transaction transaction = new Transaction(senderUserId, receiverUserId, amount, new java.util.Date());
            senderWallet.addTransaction(transaction);
            receiverWallet.addTransaction(transaction);
            walletDAO.updateWallet(senderWallet);
            walletDAO.updateWallet(receiverWallet);


            logger.info("Transaction successful. Amount transferred: " + amount);
            return true;
        } catch (Exception e) {
            logger.info("Some exception occurred " + e.getMessage());
        }
        return false;
    }




    @Override
    public boolean processTransaction(int senderUserId, int receiverUserId, double amount) {

        boolean transactionProcessed = applyTransaction(senderUserId, receiverUserId, amount);

        if (transactionProcessed) {

            Wallet senderWallet = walletDAO.getWalletByUserId(senderUserId);
            Wallet receiverWallet = walletDAO.getWalletByUserId(receiverUserId);
            applyOffers(senderWallet, receiverWallet, amount);
            walletDAO.updateWallet(senderWallet);
            walletDAO.updateWallet(receiverWallet);
        }

        return transactionProcessed;
    }

    @Override
    public List<Transaction> getTransactions(int userId, int sortField) {

        Wallet wallet = walletDAO.getWalletByUserId(userId);

        if (wallet == null) {
            logger.warning("No wallet found for user ID " + userId);
            return Collections.emptyList();
        }

        List<Transaction> transactionList = wallet.getTransactionList();

        switch (sortField) {
            case 1:
                Collections.sort(transactionList, Comparator.comparingDouble(Transaction::getAmount));
                break;

            case 2:
                Collections.sort(transactionList, Comparator.comparing(Transaction::getTime));
                break;

            default:
                logger.warning("Invalid sort field. Please use 1 for amount or 2 for date.");
                return transactionList;
        }

        for (Transaction transaction : transactionList) {

            System.out.println(" Sender ID: " + transaction.getSenderId() +
                    ", Receiver ID: " + transaction.getReceiverId() +
                    ", Amount: " + transaction.getAmount() +
                    ", Time: " + transaction.getTime());
        }


        return transactionList;

    }

    private boolean validateTransaction(Wallet senderWallet, double amount) {

        if (amount <= 0) {
            logger.warning("Transaction amount must be greater than 0.");
            return false;
        }

        if (senderWallet.getBalanceAmount() < amount) {
            logger.warning("Insufficient balance in the sender's wallet.");
            return false;
        }

        return true;
    }

    public void applyOffers(Wallet senderWallet, Wallet receiverWallet, double amount) {

        if(senderWallet.getBalanceAmount() == receiverWallet.getBalanceAmount()) {
            senderWallet.addBalanceAmount(amount/10);
            receiverWallet.addBalanceAmount(amount/10);
        }

        if (amount > 100) {
            senderWallet.addBalanceAmount(amount/10);
        }
    }
}
