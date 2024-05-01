package org.javaacademy.bitcoin_service.dto;

import java.util.Map;
import lombok.Data;

@Data
public class BpiDtoRs {
    private Map<String, CurrencyDto> bpi;
}
