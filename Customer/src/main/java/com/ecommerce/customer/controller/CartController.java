package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.model.User;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.ShoppingCartService;
import com.ecommerce.library.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final UserService userService;

    private final ProductService productService;

    private final ShoppingCartService shoppingCartService;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session) {
        if(principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();
        if(shoppingCart == null) {
            model.addAttribute("check", "No item in Cart");
        }
        session.setAttribute("totalItems", shoppingCart.getTotalQuantity());
        model.addAttribute("subTotal", shoppingCart.getTotalPrice());
        model.addAttribute("shoppingCart", shoppingCart);
        return "cart";
    }

    @PostMapping("/add-to-cart")
    public String addProductCart(@RequestParam("id") Long productId, @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                                 Principal principal, HttpServletRequest request) {
        if(principal == null) {
            return "redirect:/login";
        }
        Product product = productService.getProductById(productId);
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = shoppingCartService.addItemToCart(product, quantity, user);
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateItem(@RequestParam("id") Long productId, @RequestParam("quantity") int quantity, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(principal.getName());
        Product product = productService.getProductById(productId);
        ShoppingCart shoppingCart = shoppingCartService.updateItemInCart(product, quantity, user);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItem(@RequestParam("id") Long productId, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(principal.getName());
        Product product = productService.getProductById(productId);
        ShoppingCart shoppingCart = shoppingCartService.deleteItemInCart(product, user);
        return "redirect:/cart";
    }
}
