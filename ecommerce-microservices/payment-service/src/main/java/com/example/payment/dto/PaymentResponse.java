package com.example.payment.dto;

import com.example.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Integer paymentId;
    private  Integer orderId;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private String transactionId;
}
