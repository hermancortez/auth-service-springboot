package com.example.auth.application.usecases;

import com.example.auth.domain.entities.User;
import com.example.auth.domain.ports.input.AuthUseCase;
import com.example.auth.domain.ports.output.IUserRepository;
import com.example.auth.domain.valueobjects.Token;
import com.example.auth.infrastructure.security.JwtUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class AuthUseCaseImpl implements AuthUseCase {
    private final IUserRepository userRepository;
    private final JwtUtils jwtUtils;

    public AuthUseCaseImpl(IUserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Token login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        User user = userOpt.get();
        if (!user.isEnabled() || !password.equals(user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String jwt = jwtUtils.generateToken(user);
        String refresh = jwtUtils.generateRefreshToken(user); // <-- necesitas este método
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(3600);

        return new Token(jwt, refresh, now, expiry);

    }

    @Override
    public Token refresh(String refreshToken) {
        if (!jwtUtils.isRefreshToken(refreshToken)) {
            throw new RuntimeException("Refresh token inválido");
        }

        String username = jwtUtils.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String newAccessToken = jwtUtils.generateToken(user);
        String newRefreshToken = jwtUtils.generateRefreshToken(user);

        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(3600);

        return new Token(newAccessToken, newRefreshToken, now, expiry);
    }


    @Override
    public void logout(String accessToken) {
        jwtUtils.revokeToken(accessToken);
    }

    @Override
    public User getCurrentUser(String token) {
        String username = jwtUtils.extractUsername(token);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
