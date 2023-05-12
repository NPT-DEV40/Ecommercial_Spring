package com.ecommerce.customer.controller;

import com.ecommerce.library.model.User;
import com.ecommerce.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @GetMapping("/account")
    public String CustomerDetail(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("userDetail", user);
        return "my-account";
    }



}
