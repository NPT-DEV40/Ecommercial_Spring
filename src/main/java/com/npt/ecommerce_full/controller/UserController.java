package com.npt.ecommerce_full.controller;

import com.npt.ecommerce_full.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(value = "/account")
public class UserController {
    @GetMapping(value = "/register")
    private String addNewStudent(Model model) {
        model.addAttribute("student", "abc");
        return "register";
    }
}
