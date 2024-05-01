package org.javaacademy.bitcoin_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.javaacademy.bitcoin_service.dto.BitcoinToRubRs;
import org.javaacademy.bitcoin_service.dto.BpiDtoRs;
import org.javaacademy.bitcoin_service.dto.FreeCurrencyRs;
import org.javaacademy.bitcoin_service.dto.CurrencyDto;
import org.javaacademy.bitcoin_service.config.RateProperty;
import org.javaacademy.bitcoin_service.entity.BitcoinToRub;
import org.javaacademy.bitcoin_service.exception.IntegrationException;
import org.javaacademy.bitcoin_service.exception.InternalException;
import org.javaacademy.bitcoin_service.repository.RateRepository;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class RateService {
    private static final String CURRENCY_NAME_USD = "USD";
    private static final String CURRENCY_NAME_RUB = "RUB";
    private static final String POSTFIX_URL_GET_RUB_TEMPLATE = "/latest?currencies=%s";
    private RestTemplate restTemplate;
    private RateProperty rateProperty;
    private RateRepository rateRepository;

    public BigDecimal takeCostNow() {
        BigDecimal costBitCoinUsd = takeCostBitCoinUsd();
        BigDecimal currencyRateRub = takeCurrencyRateRub();
        BigDecimal costBitCoinRub = costBitCoinUsd.multiply(currencyRateRub);
        rateRepository.add(new BitcoinToRub(costBitCoinRub, LocalDateTime.now()));
        return costBitCoinRub;
    }

    private BigDecimal takeCostBitCoinUsd() {
        RequestEntity<Void> request = RequestEntity.get(rateProperty.getCoinDesk().getBaseUrl()).build();
        ResponseEntity<BpiDtoRs> response = takeResponse(request, BpiDtoRs.class);
        try {
            BpiDtoRs bpiDtoRq = response.getBody();
            CurrencyDto currencyDto = bpiDtoRq.getBpi().get(CURRENCY_NAME_USD);
            return currencyDto.getRateFloat();
        } catch (Exception e) {
            throw new InternalException("Ой, что-то пошло не так!");
        }
    }

    private BigDecimal takeCurrencyRateRub() {
        RequestEntity<Void> request = RequestEntity.get(rateProperty.getFreeCurrency().getBaseUrl()
                        + POSTFIX_URL_GET_RUB_TEMPLATE.formatted(CURRENCY_NAME_RUB))
                .header(rateProperty.getFreeCurrency().getHeaderTokenName(), rateProperty.getFreeCurrency().getToken())
                .build();
        ResponseEntity<FreeCurrencyRs> response = takeResponse(request, FreeCurrencyRs.class);
        try {
            FreeCurrencyRs freeCurrencyRs = response.getBody();
            return freeCurrencyRs.getData().get(CURRENCY_NAME_RUB);
        } catch (Exception e) {
            throw new InternalException("Ой, что-то пошло не так!");
        }
    }

    private <T> ResponseEntity<T> takeResponse(RequestEntity<Void> request, Class<T> type) {
        ResponseEntity<T> response;
        try {
            response = restTemplate.exchange(request, type);
        } catch (Throwable e) {
            throw new IntegrationException(((RequestEntity.UriTemplateRequestEntity<?>) request).getUriTemplate() + " unavailable");
        }
        return response;
    }

    public List<BitcoinToRubRs> takeAllCost() {
        return rateRepository.getAllCost().stream().map(this::convertToBitcoinToRubRs).toList();
    }

    private BitcoinToRubRs convertToBitcoinToRubRs(BitcoinToRub bitcoinToRub) {
        return new BitcoinToRubRs(bitcoinToRub.getRateInRub(), bitcoinToRub.getDateTime());
    }

    public BigDecimal takeAverageCost() {
        return rateRepository.takeAverageCost();
    }
}
