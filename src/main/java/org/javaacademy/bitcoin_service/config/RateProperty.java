package org.javaacademy.bitcoin_service.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "integration")
@RequiredArgsConstructor
@Getter
@Setter
@Configuration
public class RateProperty {
    private CoinDeskConfig coinDesk;
    private FreeCurrencyConfig freeCurrency;
}


