package org.javaacademy.bitcoin_service.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NonNull;

@Data
public class BitcoinToRub {
    @NonNull
    private BigDecimal rateInRub;
    @NonNull
    private LocalDateTime dateTime;
}
