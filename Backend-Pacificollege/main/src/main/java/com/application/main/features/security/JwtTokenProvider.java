package com.application.main.features.security;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    
    @Value("${juan.app.jwtSecret}")
    private String jwtSecret;
    
    @Value("${juan.app.jwtExpirationMs}")
    private int jwtExpirationInMs;
    
    public String generarToken(Authentication authentication) {
        String username = authentication.getName();

        // Obtener temas de privilegios si existen
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Crear privilegios en caso que no tengan
        Claims claims = Jwts.claims().setSubject(username);
        if (authorities.isEmpty()) {
            claims.put("ALL",
                    authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
        }
        
        Date fechaActual = new Date();
        Date fechaExpiration = new Date(fechaActual.getTime() + jwtExpirationInMs);
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        
        String token = Jwts.builder()
                .setSubject(username)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(fechaExpiration)
                .signWith(secretKey).compact();
        
        return token;
    }
    
    public String obtenerUsernameDelJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    
    public boolean validarToken(String token) {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));

            Jws<Claims> claims = Jwts
                    .parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(token);
            // Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }
        catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
