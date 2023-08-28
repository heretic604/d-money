package com.heretic.dmoney.dto.responses;

import com.heretic.dmoney.enums.OperationStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class OperationResponse {

    private UUID operationId;
    private OperationStatus status;
    private WalletResponse sender;
    private WalletResponse receiver;
    private String currencyOut;
    private String currencyIn;
    private BigDecimal amountOut;
    private BigDecimal amountIn;
    private LocalDateTime time;
}