package com.npt.ecommerce_full.auth;

import com.npt.ecommerce_full.config.JwtService;
import com.npt.ecommerce_full.role.ERole;
import com.npt.ecommerce_full.role.Role;
import com.npt.ecommerce_full.role.RoleRepository;
import com.npt.ecommerce_full.user.User;
import com.npt.ecommerce_full.user.UserRepository;
import com.npt.ecommerce_full.user.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final  UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final   PasswordEncoder passwordEncoder;
    private final   JwtService jwtService;
    private final   AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    public MessageResponse register(RegisterRequest request) {
        Set<String> strRoles = request.getRoles();
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
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
        return MessageResponse.builder()
                .message("Register Successfully")
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
