package com.example.auth.domain.entities;

import java.util.Set;

public class User {
    private String id;
    private String username;
    private String password;
    private boolean enabled;
    private Set<String> roles;

    public User(String id, String username, String password, boolean enabled, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isEnabled() { return enabled; }
    public Set<String> getRoles() { return roles; }

    public void setPassword(String password) { this.password = password; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}
