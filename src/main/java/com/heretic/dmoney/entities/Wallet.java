package com.heretic.dmoney.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "WALLET")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "WALLET_ID")
    private UUID walletId;

    @Column(name = "WALLET_NUMBER", unique = true, updatable = false)
    private String walletNumber;

    @ManyToOne(fetch = EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @Column(name = "CURRENCY", updatable = false)
    private String currency;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @OneToMany(mappedBy = "sender")
    private List<Operation> incomeOperations;

    @OneToMany(mappedBy = "receiver")
    private List<Operation> outcomeOperations;
}