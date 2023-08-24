package com.heretic.dmoney.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "OPERATION")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "OPERATION_ID")
    private UUID operationId;

    @ManyToOne(cascade = ALL, fetch = EAGER)
    @JoinColumn(name = "SENDER_WALLET_ID")
    private Wallet sender;

    @ManyToOne(cascade = ALL, fetch = EAGER)
    @JoinColumn(name = "RECEIVER_WALLET_ID")
    private Wallet receiver;

    @Column(name = "CURRENCY_OUT", updatable = false, nullable = false)
    private String currencyOut;

    @Column(name = "CURRENCY_IN", updatable = false, nullable = false)
    private String currencyIn;

    @Column(name = "AMOUNT_OUT", updatable = false, nullable = false)
    private BigDecimal amountOut;

    @Column(name = "AMOUNT_IN", updatable = false, nullable = false)
    private BigDecimal amountIn;

    @Column(name = "TIME", updatable = false)
    private LocalDateTime time;
}