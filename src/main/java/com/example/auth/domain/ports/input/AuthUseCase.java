package com.example.auth.domain.ports.input;

import com.example.auth.domain.valueobjects.Token;

public interface AuthUseCase {
    Token login(String username, String password);
    Token refresh(String refreshToken);
    void logout(String accessToken);
}
