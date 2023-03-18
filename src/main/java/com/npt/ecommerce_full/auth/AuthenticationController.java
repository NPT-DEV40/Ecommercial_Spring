package com.npt.ecommerce_full.auth;

import com.npt.ecommerce_full.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final UserService userService;
    @PostMapping(value = "/register")
    private ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            if(userService.findByEmail(request.getEmail()).isPresent()) {
                    throw new Exception("Username existed");
            }
            return ResponseEntity.ok(authenticationService.register(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(value = "/authenticate")
    private ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(request));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong email or password");
        }
    }
}
