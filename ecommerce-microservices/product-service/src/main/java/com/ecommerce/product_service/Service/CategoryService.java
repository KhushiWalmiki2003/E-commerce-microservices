package com.ecommerce.product_service.Service;

import com.ecommerce.product_service.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto dto);
    CategoryDto updateCategory(Integer id,CategoryDto dto);

    void  deleteCategory(Integer id);

    CategoryDto getCategoryById(Integer id);
    List<CategoryDto> getAllCategories();

}
