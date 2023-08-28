package com.heretic.dmoney.dto.requests;

import com.heretic.dmoney.enums.OperationStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OperationRequest {

    private OperationStatus status;
    private WalletRequest sender;
    private WalletRequest receiver;
    private String currencyOut;
    private String currencyIn;
    private BigDecimal amountOut;
    private BigDecimal amountIn;
    private LocalDateTime time;
}