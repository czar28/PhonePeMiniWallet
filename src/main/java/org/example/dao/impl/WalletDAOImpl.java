package org.example.dao.impl;

import org.example.dao.WalletDAO;
import org.example.model.Wallet;

import java.util.HashMap;
import java.util.Map;

public class WalletDAOImpl implements WalletDAO {

    private Map<Integer, Wallet> walletMap = new HashMap<>();

    @Override
    public void addWallet(Wallet wallet) {
        walletMap.put(wallet.getUserId(), wallet);
    }

    @Override
    public Wallet getWalletByUserId(int userId) {
        Wallet wallet = null;
        wallet = walletMap.get(userId);
        if(wallet == null) {
            System.out.println("No Wallet present");
        }
        return wallet;
    }

    @Override
    public void updateWallet(Wallet wallet) {

        if (walletMap.containsKey(wallet.getUserId())) {
            walletMap.put(wallet.getUserId(), wallet);
        } else {
            System.out.println("Wallet not found for User ID " + wallet.getUserId());
        }
    }


}
