package org.javaacademy.bitcoin_service.config;

import lombok.Data;

@Data
public class FreeCurrencyConfig {
    private String token;
    private String baseUrl;
    private String headerTokenName;
}
