package dev.uhanov.example.javasuggestion.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Employ {
    private long id;
    private String name;
    private String password;
    private Set<Title> title;
}
