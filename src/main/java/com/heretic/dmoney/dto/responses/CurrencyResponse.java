package com.heretic.dmoney.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyResponse {

    @JsonProperty(value = "Cur_ID")
    private Integer curCode;
    @JsonProperty(value = "Date")
    private String date;
    @JsonProperty("Cur_Abbreviation")
    private String currencyAbbreviation;
    @JsonProperty("Cur_OfficialRate")
    private BigDecimal rate;
}