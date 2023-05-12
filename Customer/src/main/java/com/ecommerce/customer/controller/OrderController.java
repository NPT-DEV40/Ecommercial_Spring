package com.ecommerce.customer.controller;

import com.ecommerce.library.model.CartItem;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.model.User;
import com.ecommerce.library.service.ShoppingCartService;
import com.ecommerce.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("subTotal", shoppingCart.getTotalPrice());
        model.addAttribute("cartItems", cartItems);
        return "checkout";
    }


}
