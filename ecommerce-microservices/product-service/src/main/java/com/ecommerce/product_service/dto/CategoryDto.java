package com.ecommerce.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Integer id;
    @NotBlank(message = "Category can not be empty.")
    private String name;
    private String description;
    private Integer parentId;
}
