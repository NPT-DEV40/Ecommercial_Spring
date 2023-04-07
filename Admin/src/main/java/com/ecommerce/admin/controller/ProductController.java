package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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
        List<ProductDto> productDtos = productService.findAll();
        model.addAttribute("products", productDtos);
        model.addAttribute("size", productDtos.size());
        return "products";
    }

    @GetMapping("/add-product")
    public String AddProduct(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        List<Category> categoryList = categoryService.findAllByActivated();
        model.addAttribute("categories", categoryList);
        model.addAttribute("product",  new ProductDto());
        return "add-product";
    }

    @PostMapping("/save-product")
    public String SaveProduct(@ModelAttribute("product") ProductDto productDto,
                              @RequestParam("imageProduct")MultipartFile imageProduct,
                              RedirectAttributes redirectAttributes) {
        try {
            productService.save(imageProduct, productDto);
            redirectAttributes.addFlashAttribute("Success", "Add successfully");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("Failed", "Failed to add");
        }
        return "redirect:/products";
    }

    @GetMapping("/update-product/{id}")
    public String UpdateProductScreen(Model model, Principal principal, @PathVariable("id") Long id) {
        if(principal == null) {
            return "redirect:/login";
        }
        ProductDto productDto = productService.findById(id);
        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("title", "Update Product");
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);
        return "update-product";
    }



    @PostMapping("/update-product/{id}")
    public String UpdateProduct(@PathVariable("id") Long id, @ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct") MultipartFile imageProduct, RedirectAttributes redirectAttributes) {
        try {
            productService.update(imageProduct, productDto);
            redirectAttributes.addFlashAttribute("Success", "Update successfully");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("Failed", "Failed to update");
        }
        return "redirect:/products";
    }

    @RequestMapping(value = "/enable-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String EnableProduct(@PathVariable("id") Long id, Model model) {
        try {
            productService.enableById(id);
            model.addAttribute("Success", "Enabled successfully");
        } catch (Exception e) {
            model.addAttribute("Failed", "Failed to enable");
        }
        return "redirect:/products";
    }

    @RequestMapping(value = "/delete-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String DeleteProduct(@PathVariable("id") Long id, Model model) {
        try {
            productService.deleteById(id);
            model.addAttribute("Success", "Deleted successfully");
        } catch (Exception e) {
            model.addAttribute("Failed", "Failed to delete");
        }
        return "redirect:/products";
    }



}
