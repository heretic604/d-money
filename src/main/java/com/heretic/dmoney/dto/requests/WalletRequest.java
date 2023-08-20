package com.heretic.dmoney.dto.requests;

import com.heretic.dmoney.dto.responses.OperationResponse;
import com.heretic.dmoney.entities.Person;
import lombok.Builder;
import lombok.Data;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.util.Set;

@Data
@Builder
public class WalletRequest {

    private String walletNumber;
    private Person person;
    private CurrencyUnit currency;
    private MonetaryAmount amount;
    private Set<OperationResponse> incomeOperations;
    private Set<OperationResponse> outcomeOperations;
}