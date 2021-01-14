package service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.ejb.Stateless;

@Stateless
public class JWTService implements JWTServiceLocal {

    private final int NUMBER_OF_MINUTES = 30;
    private final String SECRET_KEY = "secret";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public String generateToken(String login) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, login);
    }

    private String createToken(Map<String, Object> claims, String login) {
        return Jwts.builder().setClaims(claims).setSubject(login).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * NUMBER_OF_MINUTES))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    @Override
    public Boolean validateToken(String token, String login) {
        final String username = extractUsername(token);
        return (username.equals(login) && !isTokenExpired(token));
    }

}
