package com.npt.ecommerce_full;

import com.npt.ecommerce_full.user.User;
import com.npt.ecommerce_full.security.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class EcommerceFullApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceFullApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new User(null, "Nguyen", "Phu Tan", "npt", "123", new ArrayList<>()));
            userService.saveUser(new User(null, "Nguyen", "Van A", "lll", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Le", "Tung Duong", "ooo", "12345", new ArrayList<>()));
            userService.saveUser(new User(null, "Ha", "Le B", "sss", "789", new ArrayList<>()));

            userService.addRoleToUser("npt", "ROLE_USER");
            userService.addRoleToUser("lll", "ROLE_ADMIN");
            userService.addRoleToUser("lll", "ROLE_MANAGER");
            userService.addRoleToUser("ooo", "ROLE_MANAGER");
            userService.addRoleToUser("sss", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("sss", "ROLE_ADMIN");
            userService.addRoleToUser("sss", "ROLE_USER");
        };
    } // Permit data can be load before application running
}
