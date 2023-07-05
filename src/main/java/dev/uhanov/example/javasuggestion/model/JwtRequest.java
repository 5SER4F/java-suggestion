package dev.uhanov.example.javasuggestion.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {

    private String name;
    private String password;
}
