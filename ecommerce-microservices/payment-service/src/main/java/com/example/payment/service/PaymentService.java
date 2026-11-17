package com.example.payment.service;

import com.example.payment.dto.PaymentResponse;
import com.example.payment.dto.PaymentStatusUpdateRequest;
import com.example.payment.dto.RequestPayment;

public interface PaymentService {
    PaymentResponse createPayment(RequestPayment requestPayment);

    PaymentResponse getPaymentByOrderId(Integer orderId);

    PaymentResponse updatePaymentStatus(Integer PaymentId, PaymentStatusUpdateRequest request);
}
