package org.example.service;

import org.example.model.Transaction;
import org.example.model.Wallet;

import java.util.List;

public interface TransactionManagementService {


    boolean processTransaction(int senderUserId, int receiverUserId, double amount);
    List<Transaction> getTransactions(int userId, int sortField);
}
