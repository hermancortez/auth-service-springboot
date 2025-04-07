package com.example.auth.domain.valueobjects;

import java.time.Instant;

public class Token {

    private final String token;
    private final String refreshToken;
    private final Instant issuedAt;
    private final Instant expiresAt;

    public Token(String token, String refreshToken, Instant issuedAt, Instant expiresAt) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}

