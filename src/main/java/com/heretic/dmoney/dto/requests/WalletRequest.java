package com.heretic.dmoney.dto.requests;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletRequest {

    private String walletNumber;
    private String currency;
    private BigDecimal amount;
}