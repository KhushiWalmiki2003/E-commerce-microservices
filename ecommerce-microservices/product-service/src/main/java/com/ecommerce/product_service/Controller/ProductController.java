package com.ecommerce.product_service.Controller;

import com.ecommerce.product_service.Service.ProductService;
import com.ecommerce.product_service.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
  @Autowired
  ProductService productService;

  @PostMapping()
  public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto dto){
    return ResponseEntity.ok(productService.createProduct(dto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDto> updateproduct(@PathVariable Integer id, @RequestBody ProductDto dto){
    return ResponseEntity.ok(productService.updateProduct(id,dto));
  }

 @DeleteMapping("/{id}")
  public  ResponseEntity<String> deleteproduct(@PathVariable Integer id){
    productService.deleteProduct(id);
    return ResponseEntity.ok("Product Deleted Successfully ..");
 }

 @GetMapping("/{id}")
  public ResponseEntity<ProductDto> getProductByID(@PathVariable Integer id){
    return ResponseEntity.ok(productService.getProductById(id));
 }

 @GetMapping()
  public ResponseEntity<Page<ProductDto>> getAllProducts(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size,
          @RequestParam(defaultValue = "id")String sortBy,
          @RequestParam(defaultValue = "asc")String sortDir){
   return ResponseEntity.ok(productService.getAllProduct(page,size,sortBy,sortDir));
  }

  @GetMapping("/search")
  public ResponseEntity<Page<ProductDto>> searchProducts(
          @RequestParam String keyword,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10")int size){
    return ResponseEntity.ok(productService.searchProduct(keyword,page,size));
  }

  @GetMapping("/filter")
  public ResponseEntity<Page<ProductDto>> filterProducts(
          @RequestParam(required = false) Integer categoryId,
          @RequestParam(required = false) Double  minPrice,
          @RequestParam(required = false) Double  maxPrice,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10")int size){
    return ResponseEntity.ok(productService.filterProducts(categoryId,minPrice,maxPrice,page,size));
  }

  @PostMapping("/{id}/uploadimg")
    public  ResponseEntity<ProductDto> uploadImage(@PathVariable Integer id, @RequestParam("file")MultipartFile file)throws IOException {
      return ResponseEntity.ok(productService.uploadImage(id,file));

  }

}
