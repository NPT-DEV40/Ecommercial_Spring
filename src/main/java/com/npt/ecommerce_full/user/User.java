package com.npt.ecommerce_full.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements UserDetails { // To use methods for user information
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name())); // return a list of one GrantedAuthority that is created from the role name
        // using the SimpGrantedAuthority class
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() { // User account has expired or not
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // User account is locked or unlocked (A locked user cannot be authenticated)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // User's credentials (password) has expired or not (Expired credentials prevent authentication)
        return true;
    }

    @Override
    public boolean isEnabled() { // User is enabled or disabled (A disabled user cannot be authenticated)
        return true;
    }
}
