package com.npt.ecommerce_full.controller;

import com.npt.ecommerce_full.auth.AuthenticationRequest;
import com.npt.ecommerce_full.auth.AuthenticationResponse;
import com.npt.ecommerce_full.auth.MessageResponse;
import com.npt.ecommerce_full.common.API;
import com.npt.ecommerce_full.dto.jwtResponseDto;
import com.npt.ecommerce_full.role.ERole;
import com.npt.ecommerce_full.role.Role;
import com.npt.ecommerce_full.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/account")
@RequiredArgsConstructor
public class UserController {

    public static String registerAPI = API.BASED_URL + "/api/v1/auth/register";
    public static String loginAPI = API.BASED_URL + "/api/v1/auth/authenticate";

    private final RestTemplate restTemplate;

    @RequestMapping(value = "/login", produces = "application/json", method = {RequestMethod.GET, RequestMethod.POST})
    private String Login(Model model, AuthenticationRequest authenticationRequest) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        AuthenticationRequest request = new AuthenticationRequest();
//        request.setEmail(authenticationRequest.getEmail());
//        request.setPassword(authenticationRequest.getPassword());
//        HttpEntity<AuthenticationRequest> httpEntity = new HttpEntity<>(request, headers);
//        ResponseEntity<AuthenticationResponse> jwtResponse = restTemplate.exchange(loginAPI, HttpMethod.POST, httpEntity, AuthenticationResponse.class);
        return "login";
    }

    @GetMapping(value = "/register")
    private String Register(@ModelAttribute("user") User user, Model model, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            return "Error";
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Set<Role> roles = new HashSet<>();
            Role roleClient = new Role();
            roleClient.setId(1);
            roleClient.setName(ERole.USER);
            roles.add(roleClient);
            user.setRoles(roles);
            try {
                HttpEntity<User> httpEntity = new HttpEntity<>(user, headers);
                ResponseEntity<MessageResponse> jwtResponse = restTemplate.exchange(registerAPI, HttpMethod.POST, httpEntity, MessageResponse.class);
                request.getSession().setAttribute("jwtResponse", jwtResponse.getBody());
            } catch(HttpClientErrorException e) {
                return "Error";
            }
        }

        return "register";
    }
}
