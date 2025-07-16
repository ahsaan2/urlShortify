package com.url.shortener.security.jwt;

import com.url.shortener.service.UserDetailsImpl;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;
@Component
public class jwtUtils {
    @Value("${jwt.secret}")
    private  String jwtSecret;
    @Value("${jwt.expiration}")

    private int jwtExpirationMs;
    // Passing token -->> Authorization Header ->  Bearer <Token>
    // method to extract JWT Token from the header, to validate it
    public String getJwtFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            // exclude the Bearer and space, and return the token part
            return  bearerToken.substring(7);

        }
        return   null;
        // this method helps us to retrieve or extract JWT token from header
    }
    // Generate a Token, and it accepts the object of userDetails implementation
    public  String generateToken(UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
            return Jwts.builder()
                    .subject(username)
                    .claim("roles", roles)
                    .issuedAt(new Date())
                    .expiration(new Date((new Date().getTime() + jwtExpirationMs)))
                    .signWith(key())  //
                    .compact();


    }
    public  String getUserNameFromJwtToken(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }
    // Key for signWith
    private Key key(){
     return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateToken(String authToken){
        try{
            Jwts.parser().verifyWith((SecretKey) key())
                    .build().parseSignedClaims(authToken);
        }catch (JwtException e){
            throw new RuntimeException(e);
        }catch (IllegalArgumentException e){
            throw  new RuntimeException(e);
        }
        return true;
    }


}
