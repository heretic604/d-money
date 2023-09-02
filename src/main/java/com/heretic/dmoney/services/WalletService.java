package com.heretic.dmoney.services;

import com.heretic.dmoney.dto.requests.WalletRequest;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.entities.Wallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Service processes wallets data
 */
public interface WalletService {

    /**
     * Save new wallet in database
     *
     * @param walletRequest information about new wallet
     * @return info about new wallet
     */
    WalletResponse saveWallet(WalletRequest walletRequest, UUID personId);

    /**
     * Get wallet by ID from database
     *
     * @param id walletID
     * @return wallet info
     */
    WalletResponse getWallet(UUID id);

    /**
     * Get wallet by wallet number from database
     *
     * @param walletNumber wallet number from controller
     * @return wallet info
     */
    WalletResponse getWallet(Long walletNumber);

    Wallet getWalletForOperation(Long walletNumber);

    /**
     * Get all wallets from database
     *
     * @return List with information about all wallets
     */
    List<WalletResponse> getWallets();

    Wallet updateWallet(BigDecimal amountDelta, Wallet wallet);

    /**
     * Remove wallet from database
     *
     * @param id walletID to remove
     * @return deletion result
     */
    boolean deleteWallet(UUID id);
}