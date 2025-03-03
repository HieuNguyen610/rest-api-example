package hieu.javarestapi.service.impl;

import hieu.javarestapi.common.TokenType;
import hieu.javarestapi.exception.InvalidTokenException;
import hieu.javarestapi.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@Slf4j(topic = "JWT-SERVICE")
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.expiry-minutes}")
    private long expiryMinutes;

    @Value("${jwt.access-key}")
    private String accessKey;

    @Value("${jwt.refresh-key}")
    private String refreshKey;

    @Override
    public String generateAccessToken(long userId, String username, Collection<? extends GrantedAuthority> authorities) {
        log.info("Generating access token for user {} with authorities {}", userId, authorities);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", authorities);

        return generateToken(claims, username);
    }

    @Override
    public String generateRefreshToken(long userId, String username, Collection<? extends GrantedAuthority> authorities) {
        log.info("Generating refresh token for user {} with authorities {}", userId, authorities);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", authorities);

        return generateRefreshToken(claims, username);
    }

    @Override
    public String extractUsername(String token, TokenType tokenType) throws AccessDeniedException {
        return extractClaims(token, tokenType, Claims::getSubject);
    }

    private String generateToken(Map<String, Object> claims, String username) {
        log.info("Generate access token for user {} with claims {}", username, claims);
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * expiryMinutes))
                .signWith(getKey(TokenType.ACCESS_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateRefreshToken(Map<String, Object> claims, String username) {
        log.info("Generate refresh token for user {} with claims {}", username, claims);
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * expiryMinutes))
                .signWith(getKey(TokenType.REFRESH_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(TokenType type) {
        switch (type) {
            case ACCESS_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(accessKey));
            }
            case REFRESH_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(refreshKey));
            }
            default -> throw new InvalidTokenException("Invalid token");
        }
    }

    private <T> T extractClaims(String token, TokenType type, Function<Claims, T> claimsExtractor) throws AccessDeniedException {
        final Claims claims = extractAllClaims(token, type);
        return claimsExtractor.apply(claims);
    }

    private Claims extractAllClaims(String token, TokenType type) throws AccessDeniedException {
        try {
            return Jwts.parser()
                    .setSigningKey(getKey(type))
                    .build().parseSignedClaims(token)
                   .getPayload();
        } catch (SignatureException | ExpiredJwtException e) {
            throw new AccessDeniedException("Access denied");
        }
    }
}
