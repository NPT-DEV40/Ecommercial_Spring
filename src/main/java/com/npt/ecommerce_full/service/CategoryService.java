package com.npt.ecommerce_full.service;

import com.npt.ecommerce_full.dto.CategoryDto;
import com.npt.ecommerce_full.model.Category;
import com.npt.ecommerce_full.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void save(Category category) {
        Category categorySave = new Category(category.getName());
        categoryRepository.save(categorySave);
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    public Category update(Category category) {
        Category categoryUpdate = findById(category.getId());
        categoryUpdate.setName(category.getName());
        categoryUpdate.setDeleted(category.isDeleted());
        categoryUpdate.setActivated(category.isActivated());
        return categoryRepository.save(categoryUpdate);
    }

    public void deleteById(Integer id) {
        Category category = findById(id);
        category.setActivated(false);
        category.setDeleted(true);
        categoryRepository.save(category);
    }

    public void enabledById(Integer id) {
        Category category = findById(id);
        category.setActivated(true);
        category.setDeleted(false);
        categoryRepository.save(category);
    }

    public List<Category> findAllByActivated() {
        return categoryRepository.findAllActivated();
    }

    public List<CategoryDto> findCategoryAndProduct() {
        return categoryRepository.getCategoryAndProduct();
    }
}
