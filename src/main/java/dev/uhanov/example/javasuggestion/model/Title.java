package dev.uhanov.example.javasuggestion.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Title implements GrantedAuthority {

    WORKER("WORKER"),
    MANAGER("MANAGER");

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }

}
