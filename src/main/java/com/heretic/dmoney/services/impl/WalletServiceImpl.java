package com.heretic.dmoney.services.impl;

import com.heretic.dmoney.dto.requests.WalletRequest;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.entities.Wallet;
import com.heretic.dmoney.mappers.WalletMapper;
import com.heretic.dmoney.repositories.PersonRepository;
import com.heretic.dmoney.repositories.WalletRepository;
import com.heretic.dmoney.services.WalletService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.heretic.dmoney.util.Constants.ENTITY_NOT_FOUND_BY_ID;
import static com.heretic.dmoney.util.Constants.WALLET_NOT_FOUND_BY_USERNAME;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletMapper mapper;
    private final WalletRepository walletRepository;
    private final PersonRepository personRepository;

    @Override
    public WalletResponse saveWallet(WalletRequest walletRequest, UUID personID) {
        Wallet wallet = mapper.mapToWallet(walletRequest);
        personRepository.findById(personID).ifPresent(wallet::setPerson);
        Wallet savedWallet = walletRepository.save(wallet);
        return mapper.mapToWalletResponse(savedWallet);
    }

    @Override
    public WalletResponse getWallet(UUID id) {
        return walletRepository.findById(id)
                .map(mapper::mapToWalletResponse)
                .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID, id)));
    }

    @Override
    public WalletResponse getWallet(Long walletNumber) {
        return walletRepository.findByWalletNumber(walletNumber)
                .map(mapper::mapToWalletResponse)
                .orElseThrow(() -> new EntityNotFoundException(format(WALLET_NOT_FOUND_BY_USERNAME, walletNumber)));
    }

    public Wallet getWalletForOperation(Long walletNumber) {
        return walletRepository.findByWalletNumber(walletNumber)
                .orElseThrow(() -> new EntityNotFoundException(format(WALLET_NOT_FOUND_BY_USERNAME, walletNumber)));
    }

    @Override
    public List<WalletResponse> getWallets() {
        return walletRepository.findAll()
                .stream()
                .map(mapper::mapToWalletResponse)
                .toList();
    }

    @Override
    public Wallet updateWallet(BigDecimal amountDelta, Wallet wallet) {
        wallet.setAmount(wallet.getAmount().add(amountDelta));
        return walletRepository.save(wallet);
    }

    @Override
    public boolean deleteWallet(UUID id) {
        walletRepository.deleteById(id);
        return true;
    }
}