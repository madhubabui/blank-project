package com.college.ppp.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public User register(String username, String rawPassword, Role role) {
        User u = new User();
        u.setUsername(username);
        u.setPasswordHash(encoder.encode(rawPassword));
        u.setRole(role);
        return repo.save(u);
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }

    public List<User> list() {
        return repo.findAll();
    }
}