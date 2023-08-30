package com.heretic.dmoney.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletRequest {

    @NotBlank
    private Long walletNumber;
    @NotBlank
    private String currency;
    private BigDecimal amount;
}