package org.example.service.impl;

import org.example.dao.UserDAO;
import org.example.dao.WalletDAO;
import org.example.model.User;
import org.example.model.Wallet;
import org.example.service.UserRegistrationService;

import java.util.Objects;
import java.util.logging.Logger;

public class UserRegistrationServiceImpl implements UserRegistrationService {

    private static final Logger logger = Logger.getLogger(UserRegistrationServiceImpl.class.getName());

    private UserDAO userDAO;
    private WalletDAO walletDAO;

    public UserRegistrationServiceImpl(UserDAO userDAO, WalletDAO walletDAO) {
        this.userDAO = userDAO;
        this.walletDAO = walletDAO;
    }

    @Override
    public void registerUser(User user) {

        try {

            if(Objects.nonNull(userDAO.getUserById(user.getId()))) {
                logger.warning("User already registerd");
                return;
            }
            Wallet wallet = new Wallet(user.getId());

            userDAO.addUser(user);
            walletDAO.addWallet(wallet);
            logger.info("User registered successfully: " + user.toString());
        } catch (Exception e) {

            logger.warning("Some Error Occurred while trying to register user " + e.getMessage());
        }
    }
}
