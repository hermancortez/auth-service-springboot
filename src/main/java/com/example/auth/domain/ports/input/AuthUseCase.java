package com.example.auth.domain.ports.input;

import com.example.auth.domain.valueobjects.Token;
import com.example.auth.domain.entities.User;

public interface AuthUseCase {
    Token login(String username, String password);
    Token refresh(String refreshToken);
    void logout(String accessToken);
    User getCurrentUser(String token);
}
