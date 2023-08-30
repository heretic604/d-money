package com.heretic.dmoney.controllers;

import com.heretic.dmoney.dto.requests.WalletRequest;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.services.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping(value = "/wallet/{personID}")
    public WalletResponse saveWallet(@Valid @RequestBody WalletRequest walletRequest, @PathVariable UUID personID) {
        return walletService.saveWallet(walletRequest, personID);
    }

    @GetMapping(value = "/wallet/{id}")
    public WalletResponse getWallet(@PathVariable UUID id) {
        return walletService.getWallet(id);
    }

    @GetMapping(value = "/walletByNumber/{walletNumber}")
    public WalletResponse getWallet(@PathVariable Long walletNumber) {
        return walletService.getWallet(walletNumber);
    }

    @GetMapping(value = "/wallets")
    public List<WalletResponse> getWallets() {
        return walletService.getWallets();
    }

    @DeleteMapping(value = "/wallet/{id}")
    public boolean deleteWallet(@PathVariable UUID id) {
        return walletService.deleteWallet(id);
    }
}