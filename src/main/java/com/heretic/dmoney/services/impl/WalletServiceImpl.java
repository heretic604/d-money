package com.heretic.dmoney.services.impl;

import com.heretic.dmoney.dto.requests.WalletRequest;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.entities.Wallet;
import com.heretic.dmoney.mappers.PersonMapper;
import com.heretic.dmoney.mappers.WalletMapper;
import com.heretic.dmoney.repositories.WalletRepository;
import com.heretic.dmoney.services.PersonService;
import com.heretic.dmoney.services.WalletService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.heretic.dmoney.util.Constants.ENTITY_NOT_FOUND_BY_ID;
import static com.heretic.dmoney.util.Constants.WALLET_NOT_FOUND_BY_USERNAME;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final PersonService personService;
    private final WalletMapper walletMapper;
    private final PersonMapper personMapper;
    private final WalletRepository walletRepository;

    @Override
    @Transactional
    public WalletResponse saveWallet(WalletRequest walletRequest, UUID personId) {
        Wallet wallet = walletMapper.mapToWallet(walletRequest);
        wallet.setPerson(personMapper.mapToPerson(personService.getPerson(personId)));
        Wallet savedWallet = walletRepository.save(wallet);
        return walletMapper.mapToWalletResponse(savedWallet);
    }

    @Override
    @Transactional(readOnly = true)
    public WalletResponse getWallet(UUID id) {
        return walletRepository.findById(id)
                .map(walletMapper::mapToWalletResponse)
                .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID, id)));
    }

    @Override
    @Transactional(readOnly = true)
    public WalletResponse getWallet(Long walletNumber) {
        return walletRepository.findByWalletNumber(walletNumber)
                .map(walletMapper::mapToWalletResponse)
                .orElseThrow(() -> new EntityNotFoundException(format(WALLET_NOT_FOUND_BY_USERNAME, walletNumber)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WalletResponse> getWallets() {
        return walletRepository.findAll()
                .stream()
                .map(walletMapper::mapToWalletResponse)
                .toList();
    }

    @Override
    @Transactional
    public void updateWallet(BigDecimal amountDelta, UUID id) {
        walletRepository.updateAmountById(amountDelta, id);
    }

    @Override
    @Transactional
    public boolean deleteWallet(UUID id) {
        walletRepository.deleteById(id);
        return true;
    }
}