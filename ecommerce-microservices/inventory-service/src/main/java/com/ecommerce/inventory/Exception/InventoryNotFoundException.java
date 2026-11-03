package com.ecommerce.inventory.Exception;

public class InventoryNotFoundException extends RuntimeException
{
    public InventoryNotFoundException(String skuCode){
        super("Inventory Not Found for skuCode :"+skuCode);
    }
}
