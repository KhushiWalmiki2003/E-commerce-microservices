package com.ecommerce.inventory.Service;

import com.ecommerce.inventory.Exception.InventoryNotFoundException;
import com.ecommerce.inventory.Repository.InventoryRepo;
import com.ecommerce.inventory.Repository.ProcessedOrderRepo;
import com.ecommerce.inventory.dto.InventoryRequest;
import com.ecommerce.inventory.dto.InventoryResponse;
import com.ecommerce.inventory.entity.Inventory;
import com.ecommerce.inventory.entity.ProcessedOrder;
import com.ecommerce.inventory.event.OrderPlacedEvent;
import com.ecommerce.inventory.mapper.InventoryMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepo inventoryRepo;
    private  final InventoryMapper inventoryMapper;
    private final ProcessedOrderRepo orderRepo;

    public InventoryResponse checkInventory(String skuCode){
        Inventory  inventory =inventoryRepo.findBySkuCode(skuCode)
                .orElseThrow(()->new InventoryNotFoundException(skuCode));
        return inventoryMapper.toResponse(inventory);
    }

    public void addInventory(InventoryRequest request){
        Inventory inventory=inventoryMapper.toEntity(request);
        inventoryRepo.save(inventory);
    }

    @Transactional
    public void updateStock(OrderPlacedEvent event){
        if(orderRepo.existsByOrderId(event.getOrderId())){
            return;
        }
        Inventory inventory=inventoryRepo.findBySkuCode(event.getSkuCode()).orElseThrow(()->new InventoryNotFoundException(event.getSkuCode()));
        Integer availableQuantity=inventory.getQuantity();
        Integer orderQuantity=event.getQuantity();
        if(availableQuantity < orderQuantity){
            throw new RuntimeException("Insufficient stock fot this skuCode");
        }
        inventory.setQuantity(availableQuantity-orderQuantity);
        inventoryRepo.save(inventory);
        ProcessedOrder processedOrder=new ProcessedOrder();
        processedOrder.setOrderId(event.getOrderId());
        processedOrder.setProcessedAt(LocalDateTime.now());
        orderRepo.save(processedOrder);
    }


}
