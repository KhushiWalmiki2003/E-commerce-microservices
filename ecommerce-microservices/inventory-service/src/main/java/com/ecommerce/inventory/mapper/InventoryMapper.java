package com.ecommerce.inventory.mapper;

import com.ecommerce.inventory.dto.InventoryRequest;
import com.ecommerce.inventory.dto.InventoryResponse;
import com.ecommerce.inventory.entity.Inventory;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InventoryMapper {
    public Inventory toEntity(InventoryRequest request) {
        Inventory inventory = new Inventory();
        inventory.setSkuCode(request.getSkuCode());
        inventory.setQuantity(request.getQuantity());
        return inventory;
    }
    public InventoryResponse toResponse(Inventory inventory){
        return new InventoryResponse(
                inventory.getSkuCode(),
                inventory.getQuantity()>0,
                inventory.getQuantity());

    }
}
