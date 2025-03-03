package hieu.javarestapi.config;

import hieu.javarestapi.common.TokenType;
import hieu.javarestapi.service.JwtService;
import hieu.javarestapi.service.UserServiceDetail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.http.HttpHeaders;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Slf4j(topic = "CUSTOMIZE-REQUEST-FILTER")
@RequiredArgsConstructor
public class CustomizeRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserServiceDetail userServiceDetail;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Request method: {}", request.getMethod());
        log.info("Request URI: {}", request.getRequestURI());

        String authHeader = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasLength(authHeader) && authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.substring(7);
            log.info("Authorization token: {}", authHeader.substring(0, 20));

            String username = "";
            try {
                username = jwtService.extractUsername(authHeader, TokenType.ACCESS_TOKEN);
                log.info("Username : {}", username);
            } catch (UsernameNotFoundException e) {
                log.info(e.getMessage());
                throw new UsernameNotFoundException(e.getMessage());
            }

            UserDetails userDetails = userServiceDetail.UserServiceDetail().loadUserByUsername(username);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.setContext(securityContext);
            filterChain.doFilter(request, response);
        }

        filterChain.doFilter(request, response);
    }
}
