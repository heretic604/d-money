package com.heretic.dmoney.services;

import com.heretic.dmoney.dto.requests.WalletRequest;
import com.heretic.dmoney.dto.responses.WalletResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface WalletService {

    WalletResponse saveWallet(WalletRequest walletRequest);

    WalletResponse getWallet(UUID id);

    WalletResponse getWallet(String walletNumber);

    List<WalletResponse> getWallets();

    WalletResponse updateWallet(WalletRequest walletRequest, UUID id);

    boolean deleteWallet(UUID id);
}