package com.ecommerce.product_service.Impl;

import com.ecommerce.product_service.Repo.CategoryRepository;
import com.ecommerce.product_service.Service.CategoryService;
import com.ecommerce.product_service.dto.CategoryDto;
import com.ecommerce.product_service.entity.Category;
import com.ecommerce.product_service.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public CategoryDto createCategory(CategoryDto dto) {
        Category category=categoryMapper.toEntity(dto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(Integer id, CategoryDto dto) {
        Category category=categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setParentId(dto.getParentId());
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Integer id) {
      categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        return categoryRepository.findById(id).map(categoryMapper::toDto)
                .orElseThrow(()->new RuntimeException("Category Not Found."));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto).toList();
    }
}
