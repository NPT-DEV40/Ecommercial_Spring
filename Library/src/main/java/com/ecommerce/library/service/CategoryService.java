package com.ecommerce.library.service;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void update(Category updateCategory) {
        Category category = categoryRepository.findById(updateCategory.getId()).orElseThrow();
        category.setName(updateCategory.getName());
        category.set_activated(updateCategory.is_activated());
        category.set_deleted(updateCategory.is_deleted());
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    public List<Category> findAllByActivated() {
        return categoryRepository.findAllByActivated();
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public void enabledById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.set_activated(true);
        category.set_deleted(false);
        categoryRepository.save(category);
    }

    public List<CategoryDto> getCategoryAndProduct() {
        return categoryRepository.getCategoryAndProduct();
    }
}
