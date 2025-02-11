package org.example.util;

public  class Utils {

    private static int userIdCounter = 0;
    private static int walletIdCounter = 0;

    public static synchronized int generateUserId() {
        return ++userIdCounter;
    }

    public static synchronized int generateWalletId() {
        return ++walletIdCounter;
    }
}
