package com.heretic.dmoney.controllers;

import com.heretic.dmoney.dto.requests.WalletRequest;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping(value = "wallet")
    public WalletResponse saveWallet(@RequestBody WalletRequest walletRequest) {
        return walletService.saveWallet(walletRequest);
    }

    @GetMapping(value = "wallet/{id}")
    public WalletResponse getWallet(@PathVariable UUID id) {
        return walletService.getWallet(id);
    }

    @GetMapping(value = "wallet/{walletNumber}")
    public WalletResponse getWallet(@PathVariable String walletNumber) {
        return walletService.getWallet(walletNumber);
    }

    @GetMapping(value = "wallets")
    public List<WalletResponse> getWallets() {
        return walletService.getWallets();
    }

    @PutMapping(value = "wallet/{walletRequest}/{id}")
    WalletResponse updateWallet(@PathVariable WalletRequest walletRequest, @PathVariable UUID id) {
        return walletService.updateWallet(walletRequest, id);
    }

    @DeleteMapping(value = "wallet/{id}")
    public boolean deleteWallet(@PathVariable UUID id) {
        return walletService.deleteWallet(id);
    }
}