package com.example.auth.infrastructure.controller;


import com.example.auth.domain.entities.User;
import com.example.auth.domain.ports.input.AuthUseCase;
import com.example.auth.domain.valueobjects.Token;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody Map<String, String> payload, HttpServletResponse response) {
        String username = payload.get("username");
        String password = payload.get("password");
        Token token = authUseCase.login(username, password);

        // Access Token cookie
        String accessCookie = "accessToken=" + token.getToken()
                + "; HttpOnly; Secure; Path=/; Max-Age=3600; SameSite=Strict";

        // Refresh Token cookie
        String refreshCookie = "refreshToken=" + token.getRefreshToken()
                + "; HttpOnly; Secure; Path=/auth/refresh; Max-Age=604800; SameSite=Strict";

        response.addHeader("Set-Cookie", accessCookie);
        response.addHeader("Set-Cookie", refreshCookie);

        return ResponseEntity.ok().build();
    }



    @PostMapping("/refresh")
    public ResponseEntity<Token> refresh(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Token token = authUseCase.refresh(refreshToken);

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", token.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .sameSite("Strict")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", token.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/auth/refresh")
                .maxAge(604800)
                .sameSite("Strict")
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString(), refreshCookie.toString())
                .body(token);
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @CookieValue(name = "accessToken", required = false) String accessTokenCookie
    ) {
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.replace("Bearer ", "");
        } else if (accessTokenCookie != null && !accessTokenCookie.isEmpty()) {
            token = accessTokenCookie;
        }

        if (token != null && !token.isEmpty()) {
            authUseCase.logout(token);
        }

        // Expirar las cookies
        ResponseCookie accessExpired = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        ResponseCookie refreshExpired = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/auth/refresh")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        return ResponseEntity
                .noContent()
                .header(HttpHeaders.SET_COOKIE, accessExpired.toString(), refreshExpired.toString())
                .build();
    }



    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @CookieValue(name = "accessToken", required = false) String accessTokenCookie) {

        String token = null;

        // Prioriza el header si viene, si no usa la cookie
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.replace("Bearer ", "");
        } else if (accessTokenCookie != null && !accessTokenCookie.isEmpty()) {
            token = accessTokenCookie;
        }

        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            User user = authUseCase.getCurrentUser(token);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
