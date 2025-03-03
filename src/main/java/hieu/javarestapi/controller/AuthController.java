package hieu.javarestapi.controller;

import hieu.javarestapi.model.request.SignInRequest;
import hieu.javarestapi.model.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j(topic = "AUTH-CONTROLLER")
@Tag(name = "Auth Controller")
public class AuthController {

    @Operation(summary = "Access token", description = "Get access token and refresh token by username and password")
    @PostMapping("/access-token")
    public TokenResponse getAccessToken(@RequestBody SignInRequest request) {
        log.info(" Request access token");
        return TokenResponse.builder()
                .accessToken("DUMMY-ACCESS-TOKEN")
                .refreshToken("DUMMY-REFRESH-TOKEN")
                .build();
    }

    @Operation(summary = "Refresh token", description = "Get new access token and refresh token by refresh token")
    @PostMapping("/refresh-token")
    public TokenResponse getRefreshToken(@RequestBody String refreshToken) {
        log.info("Request refresh token");
        return TokenResponse.builder()
                .accessToken("DUMMY-NEW-ACCESS-TOKEN")
                .refreshToken("DUMMY-REFRESH-TOKEN")
                .build();
    }
}
