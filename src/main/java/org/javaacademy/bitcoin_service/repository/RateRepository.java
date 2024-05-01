package org.javaacademy.bitcoin_service.repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import org.javaacademy.bitcoin_service.entity.BitcoinToRub;
import org.javaacademy.bitcoin_service.exception.InternalException;
import org.springframework.stereotype.Component;

@Component

public class RateRepository {
    private LinkedList<BitcoinToRub> rateRepository = new LinkedList<>();

    public void add(BitcoinToRub bitcoinToRub) {
        rateRepository.add(bitcoinToRub);
    }

    public List<BitcoinToRub> getAllCost() {
        return rateRepository;
    }


    public BigDecimal takeAverageCost() {
        BigDecimal bigDecimal = rateRepository.stream()
                .map(BitcoinToRub::getRateInRub)
                .reduce(BigDecimal::add)
                .orElseThrow(() -> new InternalException("No rates now!"));
        return bigDecimal.divide(BigDecimal.valueOf(rateRepository.size()), 2, RoundingMode.CEILING);
    }
}
