package com.npt.ecommerce_full.repository;

import com.npt.ecommerce_full.dto.ProductDto;
import com.npt.ecommerce_full.model.Product;
import org.hibernate.boot.archive.internal.JarProtocolArchiveDescriptor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
//    Admin

//    Customer
    @Query("Select p from Product p where p.isActivated = true and p.isDeleted = false")
    List<Product> getAllProducts();
    @Query()
    List<Product> listViewProducts();
    @Query() // need to do first
    List<Product> getRelatedProducts(Integer categoryId);
    @Query() // need to do first
    List<Product> getProductsInCategory(Integer categoryId);    
    @Query() // need to do first
    List<Product> filterOrderByHighPrice();
    @Query() // need to do first
    List<Product> filterOrderByLowPrice();
}
