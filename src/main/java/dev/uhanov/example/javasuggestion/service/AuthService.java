package dev.uhanov.example.javasuggestion.service;

import dev.uhanov.example.javasuggestion.exception.AuthException;
import dev.uhanov.example.javasuggestion.model.Employ;
import dev.uhanov.example.javasuggestion.model.JwtAuthentication;
import dev.uhanov.example.javasuggestion.model.JwtRequest;
import dev.uhanov.example.javasuggestion.model.JwtResponse;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final EmployService employService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest) {
        final Employ employ = employService.getEmployByName(authRequest.getName());
        if (employ.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(employ);
            final String refreshToken = jwtProvider.generateRefreshToken(employ);
            refreshStorage.put(employ.getName(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Employ employ = employService.getEmployByName(login);
                final String accessToken = jwtProvider.generateAccessToken(employ);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Employ employ = employService.getEmployByName(login);
                final String accessToken = jwtProvider.generateAccessToken(employ);
                final String newRefreshToken = jwtProvider.generateRefreshToken(employ);
                refreshStorage.put(employ.getName(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}