package com.ecommerce.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryResponse {
    private String skuCode;
    private boolean inStock;
    private Integer availableQuantity;
}
