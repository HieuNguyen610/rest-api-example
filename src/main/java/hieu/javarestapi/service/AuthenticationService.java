package hieu.javarestapi.service;

import hieu.javarestapi.model.request.SignInRequest;
import hieu.javarestapi.model.response.TokenResponse;

import java.nio.file.AccessDeniedException;

public interface AuthenticationService {

    TokenResponse getAccessToken(SignInRequest request) throws AccessDeniedException;
    TokenResponse getRefreshToken(String refreshToken);
}
