package athat.ehubback.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import athat.ehubback.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Service
public class JwtService {

    private String jwtSecret;
    private long jwtExpirationMs;

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public void setJwtExpirationMs(long jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("role", user.getRole());
        System.out.println();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSecretKey())
                .compact();
    }

    private SecretKey getSecretKey() {
        byte[] apiKeySecretBytes = jwtSecret.getBytes();
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid JWT token");
        }
        return false;
    }

    private Claims getTokenBody(String token) {
        try {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSecretKey())
            .build()
            .parseClaimsJws(token)
            .getBody();

        } catch (Exception e) {
            throw new AccessDeniedException("Access denied: " + e.getMessage());
        }
  }

    public String extractUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) getTokenBody(token).get("role");
    }

}
