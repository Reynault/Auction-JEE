package com.ul.springauction.services.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service servant à la manipulation d'un JWT
 */
@Service
public class JwtUtil {

    private String SECRET_KEY = "secret";


    // Renvoie le login de la personne qui utilise le token
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }


    // Renvoie l'expiration du token
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }


    // Met en place une HashMap pour extraction rapide des données du token (ci-dessus)
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    // Renvoie toutes les données contenues dans le token
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }


    // Check si le token a expiré
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }


    // Génère un token
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }


    // Créé le token
    private String createToken(Map<String, Object> claims, String login){
        return Jwts.builder().setClaims(claims).setSubject(login).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }


    // Valide le token pour filtrage
    public Boolean validateToken(String token, String login){
        final String username = extractUsername(token);
        return (username.equals(login)) && !isTokenExpired(token);
    }
}
