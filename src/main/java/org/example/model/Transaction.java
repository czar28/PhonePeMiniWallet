package org.example.model;

import java.util.Date;

public class Transaction {

    private int senderId;
    private int receiverId;
    private double amount;
    private Date time;

    public Transaction(int senderId, int receiverId, double amount, Date time) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.time = time;
    }

    public double getAmount() {
        return this.amount;
    }

    public Date getTime() {
        return time;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }
}
