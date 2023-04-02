package com.npt.ecommerce_full.repository;

import com.npt.ecommerce_full.dto.CategoryDto;
import com.npt.ecommerce_full.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select c from Category c where c.isActivated= true and c.isDeleted = false")
    List<Category> findAllActivated();
    @Query() // need to be do first
    List<CategoryDto> getCategoryAndProduct();
}
