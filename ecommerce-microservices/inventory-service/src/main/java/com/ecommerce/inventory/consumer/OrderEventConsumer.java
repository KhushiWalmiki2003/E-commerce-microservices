package com.ecommerce.inventory.consumer;

import com.ecommerce.inventory.Service.InventoryService;
import com.ecommerce.inventory.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventConsumer {
    @Autowired
    private  final InventoryService inventoryService;
    @KafkaListener(topics = "order-event",groupId = "inventory-group")
    public void consume(OrderPlacedEvent event){
        inventoryService.updateStock(event);

    }
}
