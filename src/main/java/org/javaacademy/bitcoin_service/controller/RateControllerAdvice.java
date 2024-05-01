package org.javaacademy.bitcoin_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.javaacademy.bitcoin_service.exception.IntegrationException;
import org.javaacademy.bitcoin_service.exception.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RateControllerAdvice {

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<String> handleInternalException(InternalException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("На сайте наблюдаются проблемы, приходите позже!");
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<String> handleIntegrationException(IntegrationException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Внешний сервис валют не доступен!");
    }


}
