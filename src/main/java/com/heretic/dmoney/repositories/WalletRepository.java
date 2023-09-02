package com.heretic.dmoney.repositories;

import com.heretic.dmoney.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    Optional<Wallet> findByWalletNumber(Long walletNumber);

    @Modifying
    @Transactional
    @Query("UPDATE Wallet AS wallet SET wallet.amount = wallet.amount + :delta WHERE wallet.walletId = :id")
    void updateAmountById(BigDecimal delta, UUID id);
}