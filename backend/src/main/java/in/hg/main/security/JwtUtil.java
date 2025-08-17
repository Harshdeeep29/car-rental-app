package in.hg.main.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "my-secret-key-my-secret-key-my-secret-key!"; 
    // NOTE: HS256 requires key size of at least 256 bits (32 bytes), so make sure your secret is long enough

    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    // Create SecretKey from your secret string
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)  // just pass the SecretKey, no SignatureAlgorithm needed
                .compact();
    }

    public static String extractEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)  // set the key
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
