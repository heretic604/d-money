package com.heretic.dmoney.dto.responses;

import com.heretic.dmoney.entities.Person;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class WalletResponse {

    private UUID walletId;
    private String walletNumber;
    private Person person;
    private String currency;
    private BigDecimal amount;
    private Set<OperationResponse> incomeOperations;
    private Set<OperationResponse> outcomeOperations;
}