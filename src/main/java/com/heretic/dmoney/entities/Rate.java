package com.heretic.dmoney.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.CurrencyUnit;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "RATE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "RATE_ID", unique = true, updatable = false, nullable = false)
    private UUID rateId;

    @Enumerated(STRING)
    @Column(name = "SELL", updatable = false, nullable = false)
    private CurrencyUnit currencySell;

    @Enumerated(STRING)
    @Column(name = "BUY", updatable = false, nullable = false)
    private CurrencyUnit currencyBuy;

    @Column(name = "VALUE", nullable = false)
    private double value;

    @Column(name = "LAST_UPDATE", nullable = false)
    private LocalDate lastUpdate;
}