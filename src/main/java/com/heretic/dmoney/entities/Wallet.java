package com.heretic.dmoney.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @Column(name = "CURRENCY", updatable = false, nullable = false)
    private CurrencyUnit currency;

    @Column(name = "AMOUNT", nullable = false)
    private MonetaryAmount amount;

    @OneToMany(mappedBy = "sender")
    private Set<Operation> incomeOperations;

    @OneToMany(mappedBy = "receiver")
    private Set<Operation> outcomeOperations;
}