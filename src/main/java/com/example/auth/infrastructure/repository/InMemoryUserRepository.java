package com.example.auth.infrastructure.repository;

import com.example.auth.domain.entities.User;
import com.example.auth.domain.ports.output.IUserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public class InMemoryUserRepository implements IUserRepository {
    private final Map<String, User> users = new HashMap<>();

    public InMemoryUserRepository() {
        // Demo user (puedes eliminar esto luego)
        users.put("admin", new User("1", "admin", "admin", true, Set.of("ROLE_ADMIN")));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    @Override
    public Optional<User> findById(String id) {
        return users.values().stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    @Override
    public User save(User user) {
        users.put(user.getUsername(), user);
        return user;
    }
}
