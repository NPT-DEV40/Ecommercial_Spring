package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.model.User;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession httpSession) {
        if(principal != null) {
            User user = userService.findByUsername(principal.getName());
            httpSession.setAttribute("username", principal.getName());
            ShoppingCart shoppingCart = user.getShoppingCart();
            httpSession.setAttribute("shoppingCart", shoppingCart);
            int totalQuantity = shoppingCart.getTotalQuantity();
            httpSession.setAttribute("totalItems", totalQuantity);
        } else {
            httpSession.removeAttribute("username");
        }
        return "home";
    }

    @GetMapping("/home")
    public String index(Model model) {
        List<Category> categories = categoryService.findAll();
        List<ProductDto> products = productService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/product-detail")
    public String productDetails() {
        return "product-detail";
    }

    @GetMapping("/contact-us")
    public String contactUs() {
        return "contact-us";
    }


}
