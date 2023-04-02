package com.npt.ecommerce_full.controller;

import com.npt.ecommerce_full.dto.ProductDto;
import com.npt.ecommerce_full.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public void ListProduct() {

    }
}
