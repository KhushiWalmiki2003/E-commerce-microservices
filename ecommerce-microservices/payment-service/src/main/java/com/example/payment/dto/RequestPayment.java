package com.example.payment.dto;

import com.example.payment.enums.PaymentMethods;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestPayment {
    @NotNull
    private Integer orderId;
    @Positive
    private BigDecimal amount;
    @NotNull
    private PaymentMethods paymentMethods;
}
