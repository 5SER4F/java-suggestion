package dev.uhanov.example.javasuggestion.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;
@Getter
@Setter
public class JwtAuthentication implements Authentication {

    private boolean authenticated;
    private String name;

    private Set<Title> title;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return title; }

    @Override
    public Object getCredentials() { return null; }

    @Override
    public Object getDetails() { return null; }

    @Override
    public Object getPrincipal() { return name; }

    @Override
    public boolean isAuthenticated() { return authenticated; }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    public String getTitle() {
        return title.toString();
    }

        @Override
    public String getName() { return name; }

}