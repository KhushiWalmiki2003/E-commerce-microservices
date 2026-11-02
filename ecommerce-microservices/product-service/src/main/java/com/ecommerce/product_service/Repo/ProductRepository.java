package com.ecommerce.product_service.Repo;

import com.ecommerce.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);
    Page<Product> findByPriceBetween(Double min,Double max,Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String keyword,Pageable pageable);

    @Query("SELECT p FROM Product p"+
    "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword,'%'))"+
    "OR LOWER(p.description) LIKE LOWER(CONCAT('%',:keyword,'%'))")
    Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p WHERE Product p"+
            "WHERE (:keyword IS NULL OR LOWER(p,name)  LIKE LOWER(CONCAT('%',:keyword,'%')))"+
    "AND (:categoryId IS NULL OR p.category.id=:categoryId"+
    "AND(p.price BETWEEN: minPrice AND :maxprice)")
    Page<Product> advancedFilter(
            @Param("keyword") String keyword,
            @Param("categoryId") Integer categoryId,
            @Param("minPrice")Double minPrice,
            @Param("maxPrice")Double maxPrice,
            Pageable pageable
    );


}
