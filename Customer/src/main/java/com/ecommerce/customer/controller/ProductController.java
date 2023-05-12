package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/products")
    public String products(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        List<Product> productList = productService.getAllProducts();
        List<CategoryDto> categoryList = categoryService.getCategoryAndProduct();
        List<Product> listViewProducts = productService.listViewProducts();
        model.addAttribute("viewProducts", listViewProducts);
        model.addAttribute("products", productList);
        model.addAttribute("categories", categoryList);
        return "shop";
    }

    @GetMapping("/find-product/{id}")
    public String findProductById(@PathVariable("id") Long id, Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        Product product = productService.getProductById(id);
        List<Product> products = productService.getRelatedProducts(product.getCategory().getId());
        model.addAttribute("products", products);
        model.addAttribute("product", product);
        return "product-detail";
    }

    @GetMapping("/productsCategory/{id}")
    public String getProductsInCategory(@PathVariable("id") Long categoryId, Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        Category category = categoryService.findById(categoryId);
        List<CategoryDto> categoryList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.findProductsInCategory(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("categories", categoryList);
        model.addAttribute("products", products);
        return "productsInCategory";
    }

    @GetMapping("/high-price")
    public String highPrice(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        List<Product> listViewProducts = productService.listViewProducts();
        List<CategoryDto> categoryList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.filterHighPrice();
        model.addAttribute("products", products);
        model.addAttribute("viewProducts", listViewProducts);
        model.addAttribute("categories", categoryList);
        return "filter-high-price";
    }

    @GetMapping("/low-price")
    public String lowPrice(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        List<Product> listViewProducts = productService.listViewProducts();
        List<CategoryDto> categoryList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.filterLowPrice();
        model.addAttribute("products", products);
        model.addAttribute("viewProducts", listViewProducts);
        model.addAttribute("categories", categoryList);
        return "filter-low-price";
    }
}
