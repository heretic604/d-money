package com.heretic.dmoney.repositories;

import com.heretic.dmoney.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    Optional<Wallet> findByWalletNumber(String walletNumber);

//    Optional<Wallet> updateWalletByWalletId(Wallet wallet, UUID id);
}