package com.ecommerce.library.repository;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //    Admin
    @Query("select p from Product p where p.description like %?1% or p.name like %?1%")
    List<Product> searchProductsList(String searchKey);


    //    Customer
    @Query("select p from Product p where p.is_activated = true and p.is_deleted = false")
    List<Product> getAllProducts();
    @Query("select p from Product p where p.is_activated = true and p.is_deleted = false order by rand() asc limit 4")
    List<Product> listViewProducts();

    @Query("select p from Product p inner join Category c on p.category.id = c.id where c.id = ?1 and p.is_deleted = false and p.is_activated = true")
    List<Product> findProductInCategory(Long categoryId);

    @Query("select p from Product p inner join Category c where p.category.id = c.id and p.category.id = ?1")
    List<Product> getRelatedProducts(Long categoryId);

    @Query("select p from Product p where p.is_activated = true and p.is_deleted = false order by p.costPrice desc")
    List<Product> filterHighPrice();

    @Query("select p from Product p where p.is_activated = true and p.is_deleted = false order by p.costPrice")
    List<Product> filterLowPrice();
}
