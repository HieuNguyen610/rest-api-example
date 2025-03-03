package hieu.javarestapi.service;

import hieu.javarestapi.common.TokenType;
import org.springframework.security.core.GrantedAuthority;

import java.nio.file.AccessDeniedException;
import java.util.Collection;

public interface JwtService {

    String generateAccessToken(long userId, String username, Collection<? extends GrantedAuthority> authorities);
    String generateRefreshToken(long userId, String username, Collection<? extends GrantedAuthority> authorities);
    String extractUsername(String token, TokenType tokenType) throws AccessDeniedException;

}
