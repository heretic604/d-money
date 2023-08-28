package com.heretic.dmoney.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class WalletResponse {

    private UUID walletId;
    private String walletNumber;
    private UUID personID;
    private String currency;
    private BigDecimal amount;
    private List<OperationResponse> incomeOperations;
    private List<OperationResponse> outcomeOperations;
}