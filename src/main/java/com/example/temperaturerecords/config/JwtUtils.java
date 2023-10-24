package com.example.temperaturerecords.config;

import com.sun.jdi.request.InvalidRequestStateException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class JwtUtils {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String REFRESH_CLAIM = "isRefresh";

    private final Key jwtKey;
    private final long jwtExpirationMs;
    private final long jwtRefreshExpirationMs;
    private final long jwtConfirmationMs;

    public JwtUtils(@Value("${jwtSecret}") final String jwtSecret,
                    @Value("${jwtExpirationMs}") final long jwtExpirationMs,
                    @Value("${refreshExpirationMs}") final long jwtRefreshExpirationMs,
                    @Value("${jwtConfirmationMs}") final long jwtConfirmationMs) {
        this.jwtKey = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS512.getJcaName());
        this.jwtExpirationMs = jwtExpirationMs;
        this.jwtRefreshExpirationMs = jwtRefreshExpirationMs;
        this.jwtConfirmationMs = jwtConfirmationMs;
    }

    public String generateAccessToken(final String email) {
        return generateToken(email, false, jwtExpirationMs);
    }

    public String generateRefreshToken(final String email) {
        return generateToken(email, true, jwtRefreshExpirationMs);
    }

    private String generateToken(final String email, final boolean isRefresh, final long ttlMs) {
        final long nowMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(email)
                .claim(REFRESH_CLAIM, isRefresh)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(nowMillis + ttlMs))
                .signWith(jwtKey)
                .compact();
    }

    public String getSubjectFromToken(final String token) {
        String email;
        try{
            email =  Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody().getSubject();
        }catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                IllegalArgumentException exception){
            throw new InvalidRequestStateException("Invalid token.");
        }
        return email;
    }

    public boolean isValidAccessToken(final String token) {
        try {
            return !Jwts.parserBuilder().setSigningKey(jwtKey).build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get(REFRESH_CLAIM, Boolean.class);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidRefreshToken(final String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(jwtKey).build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get(REFRESH_CLAIM, Boolean.class);
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<String> getJwtFromAuthHeader(final String authHeader) {
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith(BEARER_PREFIX)) {
            return Optional.empty();
        }
        return Optional.of(authHeader.substring(BEARER_PREFIX.length()));
    }
}
