package com.aktiviti6.service;

import com.aktiviti6.model.Elena;
import com.aktiviti6.model.Elena.Role;
import com.aktiviti6.repository.ElenaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ElenaService {

    private final ElenaRepository elenaRepository;
    private final PasswordEncoder passwordEncoder;

    public Elena createElena(String email, String password, Role role) {
        if (elenaRepository.existsByEmail(email)) {
            throw new RuntimeException("Elena with this email already exists");
        }

        Elena elena = new Elena();
        elena.setEmail(email);
        elena.setPassword(passwordEncoder.encode(password));
        elena.setRole(role);

        return elenaRepository.save(elena);
    }

    public Optional<Elena> findByEmail(String email) {
        return elenaRepository.findByEmail(email);
    }

    public Optional<Elena> findById(Long id) {
        return elenaRepository.findById(id);
    }

    public List<Elena> findAll() {
        return elenaRepository.findAll();
    }
}
