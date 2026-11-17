package com.example.payment.dto;

import com.example.payment.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusUpdateRequest {
   @NotNull
   private PaymentStatus   paymentStatus;

}
