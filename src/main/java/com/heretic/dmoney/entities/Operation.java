package com.heretic.dmoney.entities;

import com.heretic.dmoney.enums.OperationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OPERATION")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "OPERATION_ID")
    private UUID operationId;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(STRING)
    private OperationStatus status;

    @ManyToOne(cascade = ALL, fetch = EAGER)
    @JoinColumn(name = "SENDER_WALLET_ID", updatable = false)
    private Wallet sender;

    @ManyToOne(cascade = ALL, fetch = EAGER)
    @JoinColumn(name = "RECEIVER_WALLET_ID", updatable = false)
    private Wallet receiver;

    @Column(name = "CURRENCY_OUT", updatable = false)
    private String currencyOut;

    @Column(name = "CURRENCY_IN", updatable = false)
    private String currencyIn;

    @Column(name = "AMOUNT_OUT", updatable = false)
    private BigDecimal amountOut;

    @Column(name = "AMOUNT_IN", updatable = false)
    private BigDecimal amountIn;

    @Column(name = "EXRATE")
    private BigDecimal exRate;

    @Column(name = "TIME", updatable = false, nullable = false)
    private LocalDateTime time;
}