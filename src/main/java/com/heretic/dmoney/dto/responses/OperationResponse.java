package com.heretic.dmoney.dto.responses;

import com.heretic.dmoney.entities.Wallet;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class OperationResponse {

    private UUID operationId;
    private Wallet sender;
    private Wallet receiver;
    private String currencyOut;
    private String currencyIn;
    private BigDecimal amountOut;
    private BigDecimal amountIn;
    private LocalDateTime time;
}