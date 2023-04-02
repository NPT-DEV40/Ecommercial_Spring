package com.npt.ecommerce_full.controller;

import com.npt.ecommerce_full.auth.RegisterRequest;
import com.npt.ecommerce_full.role.ERole;
import com.npt.ecommerce_full.role.Role;
import com.npt.ecommerce_full.role.RoleRepository;
import com.npt.ecommerce_full.user.User;
import com.npt.ecommerce_full.user.UserRepository;
import com.npt.ecommerce_full.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    private String Login() {
        return "login";
    }


    @GetMapping("/register")
    private String Register(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/do-register")
    private String register(@ModelAttribute("registerRequest") RegisterRequest registerRequest, Model model) {
        try {
            if(userService.findByEmail(registerRequest.getEmail()).isPresent()) {
                throw new Exception("Username existed");
            }
            Set<String> strRoles = registerRequest.getRoles();
            Set<Role> roles = new HashSet<>();
            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    if (role.equals("admin")) {
                        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    } else {
                        Role userRole = roleRepository.findByName(ERole.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                });
            }
            var user = User.builder() // Builder is the method build the user object, more info in this link (https://stackoverflow.com/questions/328496/when-would-you-use-the-builder-pattern)
                    .firstname(registerRequest.getFirstName())
                    .lastname(registerRequest.getLastName())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .roles(roles)
                    .build();
            userRepository.save(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
