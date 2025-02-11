package org.example.dao;

import org.example.model.Wallet;

public interface WalletDAO {

    void addWallet(Wallet wallet);
    Wallet getWalletByUserId(int userId);
    void updateWallet(Wallet wallet);
}
