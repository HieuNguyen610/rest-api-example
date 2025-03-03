package hieu.javarestapi.service.impl;

import hieu.javarestapi.model.entity.UserEntity;
import hieu.javarestapi.model.request.SignInRequest;
import hieu.javarestapi.model.response.TokenResponse;
import hieu.javarestapi.repository.UserRepository;
import hieu.javarestapi.service.AuthenticationService;
import hieu.javarestapi.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public TokenResponse getAccessToken(SignInRequest request) throws AccessDeniedException {
        log.info("Get access token");

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            log.error("Login failed, message = {}", e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        }
        var user = userRepository.findByUsername(request.getUsername());
        if (user.isEmpty()) {
            log.error("User with username = {} does not existed", request.getUsername());
            throw new AccessDeniedException("User was not found");
        } else {
            String accessToken = jwtService.generateAccessToken(user.get().getId(), request.getUsername(), user.get().getAuthorities());
            String refreshToken = jwtService.generateRefreshToken(user.get().getId(), request.getUsername(), user.get().getAuthorities());

            return TokenResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
        }
    }

    @Override
    public TokenResponse getRefreshToken(String refreshToken) {
        log.info("Get refresh token");
        return null;
    }
}
