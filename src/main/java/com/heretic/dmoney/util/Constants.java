package com.heretic.dmoney.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Constants {

    /**
     * Exception messages
     */
    public static final String INVALID_USERNAME = "Invalid username, it cannot be empty";
    public static final String INVALID_PASSWORD = "Invalid password, it cannot be empty";
    public static final String INVALID_EMAIL = "Please check your email";
    public static final String ENTITY_NOT_FOUND_BY_ID = "Nothing found by id: %s";
    public static final String USER_NOT_FOUND_BY_USERNAME = "User not found by username: %s";
    public static final String WALLET_NOT_FOUND_BY_USERNAME = "Wallet not found by number: %s";
    public static final String NO_FUNDS = "Insufficient funds to execute this operation";

    /**
     * NBRB API constants
     */
    public static final int CURRENCY_PARAMMODE = 2;
    public static final int CURRENCY_PERIODICITY = 0;
}