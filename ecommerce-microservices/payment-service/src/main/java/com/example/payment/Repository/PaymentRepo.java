package com.example.payment.Repository;

import com.example.payment.entity.payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepo extends JpaRepository<payment,Integer>{
    Optional<payment> findByOrderId(Integer orderId);
    Optional<payment> findByTransactionId(String transactionId);
}
