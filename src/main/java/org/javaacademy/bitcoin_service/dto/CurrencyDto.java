package org.javaacademy.bitcoin_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CurrencyDto {
    private String code;
    @JsonProperty("rate_float")
    private BigDecimal rateFloat;
}
