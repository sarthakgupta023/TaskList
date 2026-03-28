package com.example.tasklist.Security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // Converts secret string → a secure signing key
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Generate token from email + role
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email) // who this token belongs to
                .claim("role", role) // extra data inside token
                .setIssuedAt(new Date()) // when created
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // when expires
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // sign it
                .compact();
    }

    // Read email from token
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Read role from token
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // Check if token is expired
    public boolean isTokenValid(String token) {
        try {
            Date expiry = getClaims(token).getExpiration();
            return expiry.after(new Date()); // true if not expired
        } catch (Exception e) {
            return false; // tampered or malformed token
        }
    }

    // Internal: extract all claims from token
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}