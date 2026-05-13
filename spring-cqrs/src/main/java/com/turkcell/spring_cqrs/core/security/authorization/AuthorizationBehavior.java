package com.turkcell.spring_cqrs.core.security.authorization;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.turkcell.spring_cqrs.core.mediator.pipeline.PipelineBehavior;
import com.turkcell.spring_cqrs.core.mediator.pipeline.RequestHandlerDelegate;
import com.turkcell.spring_cqrs.core.security.UserContext;
import com.turkcell.spring_cqrs.core.security.exception.AuthenticationException;
import com.turkcell.spring_cqrs.core.security.exception.AuthorizationException;
import com.turkcell.spring_cqrs.core.security.jwt.JwtService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Order(10)
public class AuthorizationBehavior implements PipelineBehavior {

    private final JwtService jwtService;
    private final UserContext userContext;
    private final HttpServletRequest httpServletRequest;

    public AuthorizationBehavior(JwtService jwtService, UserContext userContext, HttpServletRequest httpServletRequest) {
        this.jwtService = jwtService;
        this.userContext = userContext;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public <R> R handle(Object request, RequestHandlerDelegate<R> next) {
        SecuredOperation securedOperation = request.getClass().getAnnotation(SecuredOperation.class);

        if (securedOperation == null) {
            return next.invoke();
        }

        String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AuthenticationException("Authorization token is missing or invalid");
        }

        String token = authHeader.substring(7);

        Claims claims;
        try {
            claims = jwtService.parse(token);
        } catch (Exception e) {
            throw new AuthenticationException("Invalid or expired token");
        }

        userContext.setUserId(UUID.fromString(claims.getSubject()));
        userContext.setEmail(claims.get("email", String.class));
        List<String> roles = claims.get("roles", List.class);
        userContext.setRoles(roles != null ? roles : List.of());

        String[] requiredRoles = securedOperation.roles();
        if (requiredRoles.length > 0) {
            boolean hasRole = Arrays.stream(requiredRoles).anyMatch(userContext.getRoles()::contains);
            if (!hasRole) {
                throw new AuthorizationException("You do not have permission to perform this action");
            }
        }

        return next.invoke();
    }
}
