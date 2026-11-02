package com.ecommerce.product_service.Service;

import com.ecommerce.product_service.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDto createProduct(ProductDto dto);

    ProductDto updateProduct(Integer id,ProductDto dto);

    void deleteProduct(Integer id);
    ProductDto getProductById(Integer id);

    ProductDto uploadImage(Integer productId, MultipartFile file)throws IOException;

    Page<ProductDto> getAllProduct(int page,int size,String sortBy,String sortDir);

    Page<ProductDto> searchProduct(String keyword,int page,int size);

    Page<ProductDto> filterProducts(Integer categoryId,Double minPrice,Double maxPrice,int page,int size);

    Page<ProductDto> advancedFilter(String keyword,Integer categoryId,Double minPrice,Double maxPrice,int page,int size,String sortBy,String sortDir);
}
