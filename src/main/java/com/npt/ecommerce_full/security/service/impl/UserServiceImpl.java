package com.npt.ecommerce_full.security.service.impl;

import com.npt.ecommerce_full.model.Role;
import com.npt.ecommerce_full.model.User;
import com.npt.ecommerce_full.repository.RoleRepo;
import com.npt.ecommerce_full.repository.UserRepo;
import com.npt.ecommerce_full.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService { // UserDetailsService retrieve the userdata by method loadUserByUsername(

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        User user = userRepo.findByUsername(userName);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String userName) {
        return userRepo.findByUsername(userName);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null) {
            log.error("User is not exist in the database");
            throw new UsernameNotFoundException("User is not exist in the database");
        } else {
            log.info("User is exist in the database");
        }
        Collection<SimpleGrantedAuthority> authorizes = new ArrayList<>();
        Collection<Role> roles = user.getRoles();
        for (Role role: roles) {
            authorizes.add(new SimpleGrantedAuthority(role.getName())); // Each role have an individual privilege (like admin, user, manager,...)
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorizes);
    }
}
