package com.heretic.dmoney.dto.requests;

import com.heretic.dmoney.entities.Wallet;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OperationRequest {

    private Wallet sender;
    private Wallet receiver;
    private String currencyOut;
    private String currencyIn;
    private BigDecimal amountOut;
    private BigDecimal amountIn;
    private LocalDateTime time;
}