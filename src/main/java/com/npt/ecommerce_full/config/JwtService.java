package com.npt.ecommerce_full.config;

import com.auth0.jwt.interfaces.Claim;
import com.npt.ecommerce_full.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.security.sasl.SaslClient;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// What is JWT?
// JWT is an open standard that defines a compact and self-contained way for securely transmitting information between parties as a JSON object
// This information can be verified and trusted because it is digitally signed
// The client will need to authenticate with the server using the credentials only once
// During this time, the server validates  the credentials and returns the client a JSON Web Token (JWT)
@Service
public class JwtService {

    private static final String SECRET_KEY = "7235753778214125442A472D4B6150645367566B59703373367639792F423F45"; // key generated encryption key generator

    // Retrieve username from jwt token
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject); // The subject usually an identifier for the user, such as Id or an email address
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }


    // Generate token for user
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return doGenerateToken(extraClaims, userDetails.getUsername());
    }
    // While creating the token
    // 1.Define claims of the token (Issuer, Expiration, Subject an the ID)
    // 2. Sign th JWT using the HS512 algorithm and the secret key
    // 3. According to JWS compact
    private String doGenerateToken(Map<String, Object> extraClaims, String username) {
        return Jwts
                .builder()
                .setClaims(extraClaims) // Sets claims that you want to include in the JWT payload(user roles, permissions)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Claims are data about a user that are encoded in a JWT token
    // The payload contains the claims as a JSON map
    // Retrieving any information from token we will need the secret key
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())// Sets the secret key that will be used to verify the signature of the JWT
                .build()
                .parseClaimsJws(token) // Parses the given JWT string as a JWS and returns a JWS<Claims> object that contains header, payload(JWT object) and a signature
                .getBody(); // Gets the payload of the JWS as a Claims object
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
