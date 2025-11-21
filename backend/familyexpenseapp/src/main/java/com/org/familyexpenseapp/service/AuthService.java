package com.org.familyexpenseapp.service;

import com.org.familyexpenseapp.dto.LoginRequest;
import com.org.familyexpenseapp.dto.RegisterRequest;
import com.org.familyexpenseapp.model.User;
import com.org.familyexpenseapp.repository.IncomeRepository;
import com.org.familyexpenseapp.repository.UserRepository;
import com.org.familyexpenseapp.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private IncomeRepository incomeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtProvider;

    public ResponseEntity<?> register(RegisterRequest req) {
        if (userRepo.existsByMail(req.getMail())) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        if ("admin".equalsIgnoreCase(req.getRole())) {
            return ResponseEntity.badRequest().body("Admin cannot self-register");
        }

        User user = new User();
        user.setMail(req.getMail());
        user.setPswd(passwordEncoder.encode(req.getPswd()));
        user.setRole(req.getRole().toLowerCase());
        user.setFamilyname(req.getFamilyname());

        userRepo.save(user);
        return ResponseEntity.ok("Member registered successfully");
    }

    public ResponseEntity<?> login(LoginRequest req) {
        Optional<User> userOpt = userRepo.findByMail(req.getMail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(req.getPswd(), user.getPswd())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtProvider.generateToken(user);
        return ResponseEntity.ok(Map.of("token", token, "role", user.getRole()));
    }
}

