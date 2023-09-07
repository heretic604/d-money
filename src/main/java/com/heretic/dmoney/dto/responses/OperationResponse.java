package com.heretic.dmoney.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.heretic.dmoney.enums.OperationStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
public class OperationResponse {

    private UUID operationId;
    private OperationStatus status;
    @JsonInclude(NON_NULL)
    private Long senderNumber;
    @JsonInclude(NON_NULL)
    private Long receiverNumber;
    @JsonInclude(NON_NULL)
    private String currencyOut;
    @JsonInclude(NON_NULL)
    private BigDecimal amountOut;
    @JsonInclude(NON_NULL)
    private String currencyIn;
    @JsonInclude(NON_NULL)
    private BigDecimal amountIn;
    private BigDecimal exRate;
    private LocalDateTime time;
}