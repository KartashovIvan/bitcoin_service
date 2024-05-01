package org.javaacademy.bitcoin_service.controller;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import org.javaacademy.bitcoin_service.dto.BitcoinToRubRs;
import org.javaacademy.bitcoin_service.service.RateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rate")
@AllArgsConstructor
public class RateController {
    private RateService rateService;

    @GetMapping("/now")
    public ResponseEntity<BigDecimal> takeCostNow() {
        return ResponseEntity.ok(rateService.takeCostNow());
    }

    @GetMapping("/history")
    public ResponseEntity<List<BitcoinToRubRs>> takeAllCost() {
        return ResponseEntity.ok(rateService.takeAllCost());
    }

    @GetMapping("/average")
    public ResponseEntity<BigDecimal> takeAverageCost() {
        return ResponseEntity.ok(rateService.takeAverageCost());
    }
}
