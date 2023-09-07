package com.heretic.dmoney.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletRequest {

    @NotNull
    private Long walletNumber;
    @NotBlank
    private String currency;
    private BigDecimal amount;
}