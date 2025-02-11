package org.example.model;

import org.example.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Wallet {

    private int id;
    private int userId;
    private double balanceAmount;
    List<Transaction> transactionList;

    public Wallet(int userId) {

        this.id = Utils.generateWalletId();
        this.userId = userId;
        this.balanceAmount = 0;
        transactionList = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void addTransaction(Transaction transaction) {
        transactionList.add(transaction);
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void addBalanceAmount(double amount) {
        this.balanceAmount += amount;
    }

    public void deductBalanceAmount(double amount) throws Exception {
        if(amount > this.balanceAmount) {
            throw new Exception("Invalid Transaction Amount");
        }
        this.balanceAmount -= amount;
    }
}
