package com.ecommerce.product_service.Impl;

import com.ecommerce.product_service.Repo.ProductRepository;
import com.ecommerce.product_service.Service.ProductService;
import com.ecommerce.product_service.dto.ProductDto;
import com.ecommerce.product_service.entity.Category;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl  implements ProductService {

    @Autowired
   private  ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    private final String uploadDir=System.getProperty("user.dir")+"/uploads/products";

    @Override
    public ProductDto createProduct(ProductDto dto) {
        Category category=null;
        if(dto.getCategoryId()!=null){
            category=new Category();
            category.setId(dto.getCategoryId());
        }
        Product product=productMapper.toEntity(dto,category);
        Product saved=productRepository.save(product);

        return productMapper.toDto(saved);
    }

    @Override
    public ProductDto updateProduct(Integer id, ProductDto dto) {
        Product product=productRepository.findById(id).orElseThrow(()->new RuntimeException("Product is not found."));
         product.setName(dto.getName());
         product.setDescription(dto.getDescription());
         product.setPrice(dto.getPrice());
         product.setDiscountPrice(dto.getDiscountPrice());
         if(dto.getCategoryId() !=null){
             Category category=new Category();
             category.setId(dto.getCategoryId());
             product.setCategory(category);
         }
         return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Integer id) {
     productRepository.deleteById(id);
    }

    @Override
    public ProductDto getProductById(Integer id) {
        Product product=productRepository.findById(id).orElseThrow(()->new RuntimeException("Product Not Found"));
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto uploadImage(Integer productId, MultipartFile file) throws IOException {
        Product product=productRepository.findById(productId).orElseThrow(()->new RuntimeException("ProductId not found"));
        if(file.isEmpty()){
            throw new RuntimeException("Image File is Empty");
        }long maxSize=2*1024*1024;
        if(file.getSize() >maxSize){
            throw new RuntimeException("File Size is Must be less then 2MB.");
        }
        List<String> allowedType=List.of("image/jpeg","image/png","image/jpg");
        if(!allowedType.contains(file.getContentType())){
            throw new RuntimeException("Only jpeg, png & jpg are allowed.");
        }
        String originalName=file.getOriginalFilename();
        if(originalName == null ||!originalName.startsWith(".")){
            throw new RuntimeException("Invalid file name");
        }
        String ext=originalName.substring(originalName.lastIndexOf(".")+1).toLowerCase();
        List<String> allowedExtension=List.of("jpg","png","jpeg");
        if(allowedExtension.contains(ext)){
            throw new RuntimeException("Invalid image Extensiomn .");
        }
        File folder=new File(uploadDir);
        if (!folder.exists()){
            folder.mkdirs();
        }
        String fileName= UUID.randomUUID().toString()+"."+ext;
        Path filePath=Paths.get(uploadDir+fileName);
        Files.write(filePath,file.getBytes());
        String imageUrl="/products/images/"+fileName;
        product.setImageUrl(imageUrl);
        Product saved=productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public Page<ProductDto> getAllProduct(int page, int size, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(page,size,sort);
        Page<Product> productPage=productRepository.findAll(pageable);
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> searchProduct(String keyword, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Product> productPage=productRepository.searchProducts(keyword,pageable);
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> filterProducts(Integer categoryId, Double minPrice, Double maxPrice, int page, int size) {
       Pageable pageable=PageRequest.of(page,size);
       Page<Product> products=productRepository.advancedFilter(null,categoryId,minPrice,maxPrice,pageable);
        return products.map(productMapper::toDto) ;
    }

    @Override
    public Page<ProductDto> advancedFilter(String keyword, Integer categoryId, Double minPrice, Double maxPrice, int page, int size, String sortBy, String sortDir) {
        Pageable pageable=PageRequest.of(page,size, Sort.Direction.valueOf(sortBy));
        Page<Product> productPage=productRepository.advancedFilter(keyword,categoryId,minPrice,maxPrice,pageable);
        return productPage.map(productMapper::toDto);
    }


}
