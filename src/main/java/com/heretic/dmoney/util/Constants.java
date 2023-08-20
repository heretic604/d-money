package com.heretic.dmoney.util;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;

public final class Constants {

    public static final CurrencyUnit BYN = Monetary.getCurrency("BYN");
    public static final BigDecimal START_AMOUNT = BigDecimal.valueOf(0L);

    /**
     * Exception messages
     */
    public static final String ENTITY_NOT_FOUND_MASSAGE = "User not found by id %s";

    private Constants() {
    }
}