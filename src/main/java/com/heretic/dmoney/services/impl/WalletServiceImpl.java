package com.heretic.dmoney.services.impl;

import com.heretic.dmoney.dto.requests.WalletRequest;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.repositories.WalletRepository;
import com.heretic.dmoney.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static com.heretic.dmoney.mappers.WalletMapper.INSTANCE;
import static com.heretic.dmoney.util.Constants.ENTITY_NOT_FOUND_BY_ID_MASSAGE;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public WalletResponse saveWallet(WalletRequest walletRequest) {
        return INSTANCE.toDto(walletRepository.save(INSTANCE.toEntity(walletRequest)));
    }

    @Override
    public WalletResponse getWallet(UUID id) {
        return walletRepository.findById(id)
                .map(INSTANCE::toDto)
                .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID_MASSAGE, id)));
    }

    @Override
    public WalletResponse getWallet(String walletNumber) {
        return walletRepository.findByWalletNumber(walletNumber)
                .map(INSTANCE::toDto)
                .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID_MASSAGE, walletNumber)));
    }

    @Override
    public List<WalletResponse> getWallets() {
        return walletRepository.findAll()
                .stream()
                .map(INSTANCE::toDto)
                .toList();
    }

//    @Override
//    public WalletResponse updateWallet(WalletRequest walletRequest, UUID id) {
//        return walletRepository.updateWalletByWalletId(INSTANCE.toEntity(walletRequest), id)
//                .map(INSTANCE::toDto)
//                .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID_MASSAGE, id)));
//    }

    @Override
    public boolean deleteWallet(UUID id) {
        try {
            walletRepository.deleteById(id);
            return true;
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}