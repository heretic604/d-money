package com.heretic.dmoney.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
public class WalletResponse {

    private UUID walletId;
    private Long walletNumber;
    private UUID personId;
    private String currency;
    private BigDecimal amount;
    @JsonInclude(NON_NULL)
    private List<OperationResponse> incomeOperations;
    @JsonInclude(NON_NULL)
    private List<OperationResponse> outcomeOperations;
}