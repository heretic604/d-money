package com.heretic.dmoney.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "WALLET")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "WALLET_ID", unique = true, updatable = false, nullable = false)
    private UUID walletId;

    @Column(name = "WALLET_NUMBER", unique = true, updatable = false, nullable = false)
    private String walletNumber;

    @Column(name = "PERSON_ID", updatable = false, nullable = false)
    private long userId;

    @Enumerated(STRING)
    @Column(name = "CURRENCY", updatable = false, nullable = false)
    private CurrencyUnit currency;

    @Column(name = "AMOUNT", nullable = false)
    private MonetaryAmount amount;
}