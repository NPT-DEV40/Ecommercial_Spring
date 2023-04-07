package com.ecommerce.library.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
@Service
public class JwtService {
    private static final String SECRET_KEY = "48404D635166546A576E5A7234753778214125432A462D4A614E645267556B58";

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Retrieve all the information that we need
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    public Boolean isValidToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));
    }

    // while creating the token
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the Id
    // 2. Sign the Jwt using HS256 algorithm and secret key
    // 3. According to JWS Compact
    private String doGenerateToken(Map<String, Objects> claims, String username) {
        return Jwts.builder().setClaims(claims).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateToken(Map<String, Objects> claims, UserDetails userDetails) {
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
}
