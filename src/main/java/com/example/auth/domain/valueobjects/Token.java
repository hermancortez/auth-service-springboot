package com.example.auth.domain.valueobjects;

import java.time.Instant;

public class Token {
    private final String value;
    private final Instant issuedAt;
    private final Instant expiresAt;
    private final boolean refresh;

    public Token(String value, Instant issuedAt, Instant expiresAt, boolean refresh) {
        this.value = value;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.refresh = refresh;
    }

    public String getValue() { return value; }
    public Instant getIssuedAt() { return issuedAt; }
    public Instant getExpiresAt() { return expiresAt; }
    public boolean isRefresh() { return refresh; }
}
