package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;
import com.ecommerce.library.repository.AdminRepository;
import com.ecommerce.library.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final AdminService adminService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute("Success", "Login successfully");
        model.addAttribute("Failed", "Failed to Login");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @RequestMapping("/index")
    public String home(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        return "index";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }

    @PostMapping("do-register")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                              BindingResult bindingResult,
                              Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("adminDto", adminDto);
            bindingResult.toString();
            return "register";
        }
        String username = adminDto.getUsername();
        Admin admin = adminService.findByUsername(username);
        try {
            if (admin != null) {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("Email Error", "Your email has been registered!");
                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                System.out.println("success");
                model.addAttribute("Success", "Register successfully!");
                model.addAttribute("adminDto", adminDto);
            } else {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Your password maybe wrong! Check again!");
                System.out.println("password not same");
                model.addAttribute("Failed2", "Failed to Register");
                model.addAttribute("Ok", true);
                return "register";
            }
        } catch (Exception e) {
            model.addAttribute("Failed", "Failed to Register");
            return "register";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        return "login";
    }

    @RequestMapping(value = "/dashboard")
    public String dashboard(Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        return "index";
    }

    @RequestMapping(value = "/utilities-color")
    public String utilitiesColor(Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        return "utilities-color";
    }

    @RequestMapping(value = "/utilities-border")
    public String utilitiesBorder(Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        return "utilities-border";
    }

    @RequestMapping(value = "/utilities-animation")
    public String utilitiesAnimation(Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        return "utilities-animation";
    }

    @RequestMapping(value = "/utilities-other")
    public String utilitiesOther(Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        return "utilities-other";
    }

    @RequestMapping(value = "/tables")
    public String tables(Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        return "tables";
    }

    @RequestMapping(value = "/404")
    public String error404(Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        return "404";
    }

    @RequestMapping(value = "/charts")
    public String charts(Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        return "charts";
    }

    @RequestMapping(value = "/blank")
    public String blank(Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        return "blank";
    }

    @RequestMapping(value = "/forgot-password")
    public String forgotPassword(Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        return "forgot-password";
    }
}
