package com.ecommerce.library.service;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;
import com.ecommerce.library.model.Role;
import com.ecommerce.library.repository.AdminRepository;
import com.ecommerce.library.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;

    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public Admin save(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setRoles(Collections.singletonList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }
}
