package com.finance.Atenea.service;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET = "change-this-to-32-chars-minimum-secret-key";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generate(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 1000 * 60 * 60)) // 24h (Change this)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateAndExtract(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
