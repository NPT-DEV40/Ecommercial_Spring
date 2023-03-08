package com.npt.ecommerce_full.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter { // Processes an authentication from submission

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal(); // Give user information of the current user who is authenticated (logged in)
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // Create a Hash-based Message Authentication Code (HMAC)
        String access_token = JWT.create()
                                .withSubject(user.getUsername()) // identifies the subject of the JWT
                                .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000)) // identifies the expiration time of the JWT
                                .withIssuer(request.getRequestURI().toString()) // identifies the party that created the token and signed it
                                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                                // set a custom claim called roles, which contains a list of authorities granted to the user,
                                // using Java stream to map and collect them from user.getAuthorities()
                                .sign(algorithm); // to sign and verify the JWT using secret key
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30*60*1000))
                .withIssuer(request.getRequestURI().toString())
                .sign(algorithm);
        response.addHeader("access_token", access_token);
        response.addHeader("refresh_token", refresh_token);
    }
}
