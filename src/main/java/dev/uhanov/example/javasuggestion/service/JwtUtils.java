package dev.uhanov.example.javasuggestion.service;

import dev.uhanov.example.javasuggestion.model.JwtAuthentication;
import dev.uhanov.example.javasuggestion.model.Title;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setTitle(getTitles(claims));
        jwtInfoToken.setName(claims.get("name", String.class));
        return jwtInfoToken;
    }

    private static Set<Title> getTitles(Claims claims) {
        final List<String> title = claims.get("title", List.class);
        return title.stream()
                .map(Title::valueOf)
                .collect(Collectors.toSet());
    }

}