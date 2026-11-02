package com.ecommerce.product_service.Repo;

import com.ecommerce.product_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    List<Category> findByParentId(Integer parentId);
    boolean existsByName(String name);

}
