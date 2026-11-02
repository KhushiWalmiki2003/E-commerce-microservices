package com.ecommerce.product_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Integer id;
    @NotBlank(message = "Product name is required .")
    private String name;
    @NotBlank
    private String description;
    @Positive(message = "Price must be positive.")
    private double price;
    private  double discountPrice;
    @Min(value = 0,message = "Quantity must be 0 or more")
    private int quantity;
    private String brand;
    private String imageUrl;
   @NotNull(message ="Category id is required.")
    private Integer categoryId;
}
