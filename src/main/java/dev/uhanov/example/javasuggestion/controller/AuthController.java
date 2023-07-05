package dev.uhanov.example.javasuggestion.controller;

import dev.uhanov.example.javasuggestion.model.JwtAuthentication;
import dev.uhanov.example.javasuggestion.model.JwtRequest;
import dev.uhanov.example.javasuggestion.model.JwtResponse;
import dev.uhanov.example.javasuggestion.model.RefreshJwtRequest;
import dev.uhanov.example.javasuggestion.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PreAuthorize("hasAuthority('WORKER')")
    @GetMapping("/hello/worker")
    public ResponseEntity<String> helloWorker() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello worker " + authInfo.getPrincipal() + "!");
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/hello/manager")
    public ResponseEntity<String> helloAdmin() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello manager " + authInfo.getPrincipal() + "!");
    }


    @PostMapping("/auth/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/auth/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
