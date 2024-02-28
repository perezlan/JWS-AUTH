package com.example.demojwt.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

@Service
public class JwtService { // Clase que crea el token

    private static final String SECRET_KEY = "793a8d1bcfc9fbdbb3e3fc9ebec49fd0";

    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        System.out.println("******* KEY ****** " + keyBytes);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        System.out.println("**************** getUsernameFromToken *********** " + token);

        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        System.out.println("**************** isTokenValid *********** " + token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    private Claims getAllClaimsFromToken(String token) {
        System.out.println("**************** getAllClaimsFromToken *********** + " + token);

        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        System.out.println("**************** getClaim *********** " + token);

        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);

    }

    private Date getExpiDate(String token) {
        System.out.println("****************getExpiDate  *********** " + token);

        return getClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        System.out.println("**************** isTokenExpired *********** " + token);
        final Date expiration = getExpiDate(token);
        return expiration.before(new Date());
    }
}
