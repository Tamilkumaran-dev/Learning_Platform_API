package com.app.LearningPlatformAPI.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final static String SECRET = "This a secret that no one wants to know so don't tell to anyone";
    private final static long EXPIRATION =  1000L * 60 * 60 * 24 * 30;
    private Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));


    public String generateJwtToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims decodeToken(String token){
        return Jwts.parser().setSigningKey(key).build().parseSignedClaims(token).getBody();
    }

    public Boolean isValidToken(String token){

        Claims claims = decodeToken(token);

        if(claims.isEmpty()){
            throw new RuntimeException("Token is invalid");
        }
        else{
            return true;
        }


    }

}
