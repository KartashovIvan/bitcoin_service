package org.javaacademy.bitcoin_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BitcoinToRubRs {
    private BigDecimal rateInRub;
    private LocalDateTime dateTime;
}
