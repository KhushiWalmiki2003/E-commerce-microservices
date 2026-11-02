package com.ecommerce.product_service.Controller;

import com.ecommerce.product_service.Service.CategoryService;
import com.ecommerce.product_service.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto dto){
        return ResponseEntity.ok(categoryService.createCategory(dto));
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getcategoryById(@PathVariable Integer id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> upfateCAtegory(@PathVariable Integer id,@RequestBody CategoryDto dto){
        return ResponseEntity.ok(categoryService.updateCategory(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category is deleted successfully ...");
    }

}
