package com.example.auth.domain.ports.output;

import com.example.auth.domain.entities.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> findByUsername(String username);
    Optional<User> findById(String id);
    User save(User user);
}
