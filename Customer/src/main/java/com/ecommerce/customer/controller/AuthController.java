package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.UserDto;
import com.ecommerce.library.model.User;
import com.ecommerce.library.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String Login() {
        return "login";
    }

    @GetMapping("/register")
    public String Register(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String doRegister(@Valid @ModelAttribute("userDto") UserDto userDto, Model model,
                             BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("userDto", userDto);
                return "register";
            }
            User user = userService.findByUsername(userDto.getUsername());
            if (user != null) {
                model.addAttribute("username", "Username has been registered");
                model.addAttribute("userDto", userDto);
                return "register";
            }
            if (userDto.getPassword().equals(userDto.getRepeatPassword())) {
                userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
                userService.save(userDto);
                model.addAttribute("success", "Register Successfully");
            } else {
                model.addAttribute("fail", "Wrong username or password");
                model.addAttribute("userDto", userDto);
                return "register";
            }
        } catch (Exception e) {
            model.addAttribute("error","Fail to register");
            model.addAttribute("userDto", userDto);
        }
        return "redirect:/login";
    }
}
