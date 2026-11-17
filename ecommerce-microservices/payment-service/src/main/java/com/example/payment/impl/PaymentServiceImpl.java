package com.example.payment.impl;

import com.example.payment.Repository.PaymentRepo;
import com.example.payment.dto.PaymentResponse;
import com.example.payment.dto.PaymentStatusUpdateRequest;
import com.example.payment.dto.RequestPayment;
import com.example.payment.entity.payment;
import com.example.payment.enums.PaymentStatus;
import com.example.payment.exception.PaymentNotFoundException;
import com.example.payment.service.PaymentService;
import com.example.payment.util.TransactionGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.payment.entity.payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepo paymentRepo;
    @Override
    public PaymentResponse createPayment(RequestPayment requestPayment) {
        payment paymentBuilder=payment.builder().orderId(requestPayment.getOrderId())
                .amount(requestPayment.getAmount())
                .paymentMethods(requestPayment.getPaymentMethods())
                .paymentStatus(PaymentStatus.PENDING)
                .transactionId(TransactionGenerator.generate()).build();
        payment savedPayment=paymentRepo.save(paymentBuilder);
        return mapToResponse(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentByOrderId(Integer orderId) {
        payment payment=paymentRepo.findByOrderId(orderId).orElseThrow(()->new PaymentNotFoundException("Payment Not found for this orderid "+orderId));
        return mapToResponse(payment);
    }

    @Override
    public PaymentResponse updatePaymentStatus(Integer PaymentId, PaymentStatusUpdateRequest request) {
        payment payment=paymentRepo.findById(PaymentId).orElseThrow(()->new PaymentNotFoundException("Payment not found with id "+PaymentId));
        payment.setPaymentStatus(request.getPaymentStatus());
        return mapToResponse(payment);
    }
    private PaymentResponse mapToResponse(payment payment){
        return new PaymentResponse(payment.getId(),
        payment.getOrderId(),
        payment.getAmount(),
        payment.getPaymentStatus(),
        payment.getTransactionId());

    }
}
