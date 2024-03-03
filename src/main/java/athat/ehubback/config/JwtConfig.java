package athat.ehubback.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import athat.ehubback.service.JwtService;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    @Bean
    public JwtService jwtService() {
        JwtService jwtService = new JwtService();
        jwtService.setJwtSecret(jwtSecret);
        jwtService.setJwtExpirationMs(jwtExpirationMs);
        return jwtService;
    }
}
