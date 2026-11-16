package com.ecommerce.inventory.Repository;

import com.ecommerce.inventory.entity.ProcessedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedOrderRepo extends JpaRepository<ProcessedOrder,Integer> {
    boolean existsByOrderId(Integer orderId);
}
