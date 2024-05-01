package org.javaacademy.bitcoin_service.dto;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;

@Data
public class FreeCurrencyRs {
    private Map<String, BigDecimal> data;
}
