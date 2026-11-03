package com.ecommerce.inventory.Service;

import com.ecommerce.inventory.Exception.InventoryNotFoundException;
import com.ecommerce.inventory.Repository.InventoryRepo;
import com.ecommerce.inventory.dto.InventoryRequest;
import com.ecommerce.inventory.dto.InventoryResponse;
import com.ecommerce.inventory.entity.Inventory;
import com.ecommerce.inventory.mapper.InventoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepo inventoryRepo;
    private  final InventoryMapper inventoryMapper;

    public InventoryResponse checkInventory(String skuCode){
        Inventory  inventory =inventoryRepo.findBySkuCode(skuCode)
                .orElseThrow(()->new InventoryNotFoundException(skuCode));
        return inventoryMapper.toResponse(inventory);
    }

    public void addInventory(InventoryRequest request){
        Inventory inventory=inventoryMapper.toEntity(request);
        inventoryRepo.save(inventory);
    }


}
