package com.example.payment.entity;

import com.example.payment.enums.PaymentMethods;
import com.example.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments",indexes = {
        @Index(columnList = "orderId"),
        @Index(columnList = "transactionId")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private  Integer orderId;
    @Column(nullable = false,precision = 12,scale = 2)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethods paymentMethods;
    @Column(unique = true,nullable = false)
    private String transactionId;
    private String gatewayPaymentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate(){
        this.createdAt =LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }

    @PreUpdate
    void  onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }
}
