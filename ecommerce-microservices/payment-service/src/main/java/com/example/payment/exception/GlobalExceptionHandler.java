package com.example.payment.exception;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<?> handleNotFound(PaymentNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("timestamp", LocalDateTime.now(),"error",e.getMessage()));
    }
}
