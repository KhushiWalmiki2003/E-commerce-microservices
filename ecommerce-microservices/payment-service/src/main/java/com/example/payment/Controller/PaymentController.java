package com.example.payment.Controller;

import com.example.payment.dto.PaymentResponse;
import com.example.payment.dto.PaymentStatusUpdateRequest;
import com.example.payment.dto.RequestPayment;
import com.example.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    @Autowired
    private  PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody @Valid RequestPayment requestPayment){
        PaymentResponse response=paymentService.createPayment(requestPayment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getByorderId(@PathVariable Integer orderId){
        PaymentResponse response=paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{paymentId}/status")
    public ResponseEntity<PaymentResponse> updateStatus(@PathVariable Integer PaymentId, @RequestBody @Valid PaymentStatusUpdateRequest request){
        PaymentResponse response=paymentService.updatePaymentStatus(PaymentId,request );
        return ResponseEntity.ok(response);
    }


}
