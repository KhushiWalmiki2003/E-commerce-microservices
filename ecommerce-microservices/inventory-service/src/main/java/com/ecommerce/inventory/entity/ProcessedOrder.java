package com.ecommerce.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Table(name = "Processed_order",uniqueConstraints = @UniqueConstraint(columnNames = "orderId"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessedOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,unique = true)
    private Integer orderId;
    private LocalDateTime processedAt;
}
