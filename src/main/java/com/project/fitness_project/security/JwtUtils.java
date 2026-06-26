package com.project.fitness_project.security;

import com.project.fitness_project.model.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;
    private int jwtExpirationMs = 172800000;

    public String generateToken(String userId, String userRole) {
        return Jwts.builder()
                .subject(userId)
                .claim("roles" , List.of(userRole))
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public String getUserIdFromToken(String jwt){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }


    public String getJwtTokenFromRequest(HttpServletRequest request){
        String BearerToken = request.getHeader("Authorization");
        if(BearerToken != null && BearerToken.startsWith("Bearer "))
            return BearerToken.substring(7);

        return null;
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(jwt);
            return  true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Claims getAllClaims(String jwt) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }
}
