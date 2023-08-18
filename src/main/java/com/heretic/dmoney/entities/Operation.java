package com.heretic.dmoney.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.convert.ExchangeRate;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "OPERATION")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "OPERATION_ID", unique = true, updatable = false, nullable = false)
    private UUID operationId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "SENDER_WALLET_ID")
    private Wallet sender;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "RECEIVER_WALLET_ID")
    private Wallet receiver;

    @Column(name = "CURRENCY_OUT", updatable = false, nullable = false)
    private CurrencyUnit currencyOut;

    @Column(name = "CURRENCY_IN", updatable = false, nullable = false)
    private CurrencyUnit currencyIn;

    @Column(name = "AMOUNT_OUT", updatable = false, nullable = false)
    private MonetaryAmount amountOut;

    @Column(name = "AMOUNT_IN", updatable = false, nullable = false)
    private MonetaryAmount amountIn;

    @Column(name = "EXCHANGE_RATE", updatable = false, nullable = false)
    private ExchangeRate exchangeRate;

    @Column(name = "TIME", updatable = false, nullable = false)
    private LocalDateTime time;
}